package org.stringtree.xmlevents;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.stringtree.xml.BadXMLException;
import org.stringtree.xml.UnexpectedCharacterException;

public class XMLEventParser {
    private static final String CDATA_START = "[CDATA[";
	private static final String CDATA_END = "]]";
    private static final String COMMENT_START = "--";
    private static final String COMMENT_END = "--";
    private static final String XML_START = "?xml";
    
    // State Machine states
	//
	private static final int OUTSIDE = 0;
	private static final int STARTED = 1;
	private static final int TAGNAME = 2;
	private static final int TAGSPACE = 3;
	private static final int TAGARG = 4;
	private static final int TAGEQUALS = 5;
	private static final int TAGARGVAL = 6;
	private static final int TAGARGQUOTE = 7;
	private static final int ENTITY = 8;
	private static final int DOCTYPE = 9;
	
	private static String[] statusNames = {
	  "OUTSIDE",
	  "STARTED",
	  "TAGNAME",
	  "TAGSPACE",
	  "TAGARG",
	  "TAGEQUALS",
	  "TAGARGVAL",
	  "TAGARGQUOTE",
	  "ENTITY",
	  "DOCTYPE"
	};
	
	private static String statusName(int status) {
	    return (status >= OUTSIDE && status <= DOCTYPE) 
	        ? statusNames[status] 
	        : "UNKNOWN";
	}
		
	// State machine symbol types
	//
	private static final int ORDINARY = 10;
	private static final int NAMECHAR = 20;
	private static final int LESSTHAN = 30;
	private static final int GREATERTHAN = 40;
	private static final int SPACE = 50;
	private static final int EQUALS = 60;
	private static final int SLASH = 70;
	private static final int QUOTE = 80;
	private static final int QUERY = 90;
	private static final int AMPERSAND = 100;
	private static final int SEMICOLON = 110;
	private static final int EXCLAMATION = 120;
    private static final int COLON = 130;
    
    private static String[] symbolNames = {
      "ORDINARY",
      "NAMECHAR",
      "LESSTHAN",
      "GREATERTHAN",
      "SPACE",
      "EQUALS",
      "SLASH",
      "QUOTE",
      "QUERY",
      "AMPERSANS",
      "SEMICOLON",
      "EXCLAMATION",
      "COLON"
    };
    
    private static String symbolName(int symbol) {
        return (symbol >= ORDINARY && symbol <= COLON) ? 
               symbolNames[(symbol / 10) - 1] : "UNKNOWN";
    }
		
	// are we processing a start or end tag
	//
	private static final int BLOCKSTART = 0;
	private static final int BLOCKEND = 1;
	
	// what sort of quotes are in use
	//
	private static final int NOQUOTES = 0;
	
	public static final String KEY_LAX = "~LAX";
	public static final String KEY_TRIM = "~TRIM";
    public static final String KEY_STRIPNS = "~STRIPNS";
    public static final String KEY_ENTITIES = "~ENTITIES";
	
	private Map<String, Object> parameters;
	private Map<String, String> entities;
	
	public XMLEventParser(Map<String, Object> parameters, Map<String, String> entities) {
		this.parameters = parameters;
		this.entities = entities;
	}
	
	public XMLEventParser(Map<String, Object> parameters) {
		this(parameters, defaultEntities());
	}
	
	public XMLEventParser(boolean lax, boolean trim, boolean stripns, boolean entities) {
		this.parameters = new HashMap<String, Object>();
		if (lax) parameters.put(KEY_LAX, "true");
		if (trim) parameters.put(KEY_TRIM, "true");
		if (stripns) parameters.put(KEY_STRIPNS, "true");
        if (entities) parameters.put(KEY_ENTITIES, "true");
		this.entities = defaultEntities();
	}
	
	public void setParameter(String key) {
	    parameters.put(key, "true");
	}
    
    public void resetParameter(String key) {
        parameters.remove(key);
    }
    
    public XMLEventParser(boolean lax, boolean trim) {
        this(lax, trim, true, false);
    }
	
	public XMLEventParser() {
		this(true, false);
	}

	private static final Map<String, String> defaultEntities() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("lt", "<");
		map.put("gt", ">");
		map.put("amp", "&");
		map.put("quot", "\"");
		map.put("apos", "'");
		return map;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
    public Object process(Reader in, XMLEventHandler handler, History history, Object context) throws IOException {
		int tagType = 0;
		int status = 0;
		int realstatus = status;
		HashMap args = new HashMap();

        StringBuffer namespace = new StringBuffer();
		StringBuffer tagName = new StringBuffer();
		StringBuffer argName = new StringBuffer();
        StringBuffer argNamespace = new StringBuffer();
		StringBuffer argValue = new StringBuffer();
		StringBuffer pushback = new StringBuffer();
		StringBuffer doctype = new StringBuffer();

		StringBuffer entity = new StringBuffer();
		StringBuffer text = new StringBuffer();

		int chartype = ORDINARY;
		boolean singleTagFlag = false;
		char quoteType = NOQUOTES;
		
		int line = 0;
		int column = 0;

		int i = 99; // dummy non-zero value
		
		context = handler.handle(XMLEvent.START, (History)history.clone(), null, context, line, column);

		while (i >= 0) {
			char c;
			
			if (pushback.length() > 0) {
				c = pushback.charAt(0);
				pushback.deleteCharAt(0);
				chartype = NAMECHAR;
			} else {
				i = in.read();
			
				if (i == 0) {
					continue;
				}
			
				c = (char)i;
			
				// work out the character type
				//
				switch(c) {
				case '<':
					chartype = LESSTHAN;
					break;
				case '>':
					chartype = GREATERTHAN;
					break;
				case '=':
					chartype = EQUALS;
					break;
				case '/':
					chartype = SLASH;
					break;
				case '"':
				case '\'':
                    chartype = QUOTE;
					break;
				case '?':
					chartype = QUERY;
					break;
				case '&':
					chartype = AMPERSAND;
					break;
				case ';':
					chartype = SEMICOLON;
					break;
				case '!':
					chartype = EXCLAMATION;
					break;
                case ':':
                    chartype = COLON;
                    break;
				default:
					if (isNameChar(c)) {
						chartype = NAMECHAR;
					} else if (Character.isWhitespace(c)) {
						chartype = SPACE;
					} else {
						chartype = ORDINARY;
					}
				}
				
				if (c == '\n') {
					++line;
					column = 0;
				} else {
					++column;
				}
			}
			
			if (handler instanceof XMLCharacterHandler) {
			    ((XMLCharacterHandler)handler).handle(history, c, line, column);
			}
			
			String tagNameString = tagName.toString();
			String argNameString = argName.toString();
			String argValueString = argValue.toString();
			
//System.err.println("XMLEventParser status=" + status + "(" + statusName(status) + ") char=[" + c + "] chartype=" + chartype + "(" + symbolName(chartype) + ")");
			
			// work out what to do with it
			//
			switch(status+chartype) {
			case ENTITY+ORDINARY:
			case ENTITY+NAMECHAR:
            case ENTITY+COLON:
			case ENTITY+LESSTHAN:
			case ENTITY+GREATERTHAN:
			case ENTITY+SPACE:
			case ENTITY+EQUALS:
			case ENTITY+SLASH:
			case ENTITY+QUOTE:
			case ENTITY+QUERY:
			case ENTITY+AMPERSAND:
			case ENTITY+EXCLAMATION:
				entity.append(c);
				break;

			case ENTITY+SEMICOLON:
			    String entityName = entity.toString();
			    if (isEntityEvents()) {
	                String s = trim(text.toString());
	                if (s.length() > 0) {
	                    handler.handle(XMLEvent.TEXT, (History)history.clone(), singleMap(s), context, line, column);
	                }
	                text.setLength(0);
			        handler.handle(XMLEvent.ENTITY, (History)history.clone(), singleMap(entityName), context, line, column);
			    } else {
    				String entityValue = entities.get(entityName);
    				if (entityValue != null) {
    					pushback.append(entityValue);
    				}
			    }
				entity.setLength(0);
				status = realstatus;
				break;
				
			case OUTSIDE+AMPERSAND:
			case STARTED+AMPERSAND:
			case TAGNAME+AMPERSAND:
			case TAGSPACE+AMPERSAND:
			case TAGARG+AMPERSAND:
			case TAGEQUALS+AMPERSAND:
			case TAGARGVAL+AMPERSAND:
			case TAGARGQUOTE+AMPERSAND:
				realstatus = status;
				status = ENTITY;
				break;

			case OUTSIDE+ORDINARY:
			case OUTSIDE+NAMECHAR:
            case OUTSIDE+COLON:
			case OUTSIDE+SPACE:
			case OUTSIDE+EQUALS:
			case OUTSIDE+SLASH:
			case OUTSIDE+QUOTE:
			case OUTSIDE+QUERY:
			case OUTSIDE+EXCLAMATION:
			case OUTSIDE+SEMICOLON:
				if (i > 0) {
					text.append(c);
				}
				break;
				
			case OUTSIDE+LESSTHAN:
				status = STARTED;
				tagName.setLength(0);
		    	String s = trim(text.toString());
			    if (s.length() > 0) {
			    	handler.handle(XMLEvent.TEXT, (History)history.clone(), singleMap(s), context, line, column);
			    }
		    	text.setLength(0);
				break;
				
			case STARTED+SPACE:
				// ignore spaces after '<'
				break;
				
			case STARTED+SLASH:
				tagType = BLOCKEND;
				status = TAGNAME;
				break;
				
			case STARTED+NAMECHAR:
			case STARTED+QUERY:
				tagType = BLOCKSTART;
				status = TAGNAME;
				tagName.append(c);
				break;
				
            case STARTED+EXCLAMATION:
                status = DOCTYPE;
                doctype.setLength(0);
                break;

            case DOCTYPE+ORDINARY:
			case DOCTYPE+NAMECHAR:
            case DOCTYPE+COLON:
			case DOCTYPE+LESSTHAN:
			case DOCTYPE+SPACE:
			case DOCTYPE+EQUALS:
			case DOCTYPE+SLASH:
			case DOCTYPE+QUOTE:
			case DOCTYPE+QUERY:
			case DOCTYPE+AMPERSAND:
			case DOCTYPE+SEMICOLON:
			case DOCTYPE+EXCLAMATION:
				status = DOCTYPE;
				doctype.append(c);
				break;
				
			case DOCTYPE+GREATERTHAN:
                String docvalue = doctype.toString();
			    if (docvalue.startsWith(CDATA_START) && !docvalue.endsWith(CDATA_END)) {
			        status = DOCTYPE;
	                doctype.append(c);
			    } else {
    				status=OUTSIDE;
    				if (docvalue.startsWith(CDATA_START) && docvalue.endsWith(CDATA_END)) {
    				    String payload = docvalue.substring(CDATA_START.length(), docvalue.length()-CDATA_END.length());
    					handler.handle(XMLEvent.TEXT, (History)history.clone(), singleMap(payload), context, line, column);
    				} else if (docvalue.startsWith(COMMENT_START) && docvalue.endsWith(COMMENT_END)) {
    				    String payload = docvalue.substring(COMMENT_START.length(), docvalue.length()-COMMENT_END.length());
    					handler.handle(XMLEvent.COMMENT, (History)history.clone(), singleMap(payload.trim()), context, line, column);
    			    } else {
    					handler.handle(XMLEvent.DOCTYPE, (History)history.clone(), singleMap(docvalue), context, line, column);
    				}
				    doctype.setLength(0);
			    }
				break;
				
			case TAGNAME+NAMECHAR:
				tagName.append(c);
				break;
				
            case TAGNAME+COLON:
                if (isStripped()) {
                    namespace.append(tagName);
                    tagName.setLength(0);
                } else {
                    tagName.append(c);
                }
                break;

            case TAGNAME+SPACE:
				status = TAGSPACE;
				break;
				
			case TAGNAME+SLASH:
			case TAGSPACE+SLASH:
			case TAGNAME+QUERY:
			case TAGSPACE+QUERY:
				status = TAGSPACE;
				singleTagFlag = true;
				break;
				
			case TAGSPACE+SPACE:
				break;
				
			case TAGSPACE+NAMECHAR:
				argName.append(c);
				status = TAGARG;
				break;
				
			case TAGARG+NAMECHAR:
				argName.append(c);
				break;

			case TAGARG+COLON:
			    if (isStripped()) {
                    argNamespace.append(argName);
                    argName.setLength(0);
			    } else {
	                argName.append(c);
			    }
                break;
				
			case TAGARG+SPACE:
				// ignore space after arg name
				break;
				
			case TAGARG+EQUALS:
				status = TAGEQUALS;
				break;
				
			case TAGEQUALS+SPACE:
				// ignore space after '='
				break;
				
			case TAGEQUALS+NAMECHAR:
			case TAGEQUALS+ORDINARY:
			case TAGEQUALS+SLASH:
				argValue.append(c);
				status = TAGARGVAL;
				break;
			
			case TAGEQUALS+QUOTE:
                quoteType = c;
                status = TAGARGQUOTE;
				break;

			case TAGARGQUOTE+QUOTE:
			    if (quoteType == c) {
                    status = TAGSPACE;
                    quoteType = NOQUOTES;
                    if (argName.length() > 0) {
                        args.put(argNameString, argValueString);
                        argName.setLength(0);
                    }
                    argValue.setLength(0);
                } else {
                    argValue.append(c);
                }
                break;
			
			case TAGARGVAL+NAMECHAR:
            case TAGARGVAL+COLON:
			case TAGARGVAL+ORDINARY:
			case TAGARGQUOTE+SPACE:
			case TAGARGQUOTE+ORDINARY:
			case TAGARGQUOTE+NAMECHAR:
            case TAGARGQUOTE+COLON:
			case TAGARGQUOTE+SLASH:
			case TAGARGQUOTE+LESSTHAN:
			case TAGARGQUOTE+GREATERTHAN:
			case TAGARGQUOTE+QUERY:
			case TAGARGQUOTE+EQUALS:
			case TAGARGQUOTE+EXCLAMATION:
			case TAGARGQUOTE+SEMICOLON:
				argValue.append(c);
				break;
				
			case TAGARGVAL+SPACE:
			case TAGARGVAL+SLASH:
			case TAGARGVAL+QUERY:
				if (argName.length() > 0) {
					args.put(argNameString, argValueString);
					argName.setLength(0);
				}
				argValue.setLength(0);
				status = TAGSPACE;
				break;
				
			case TAGARGVAL+GREATERTHAN:
			case TAGNAME+GREATERTHAN:
			case TAGSPACE+GREATERTHAN:
				if (argName.length() > 0) {
					args.put(argNameString, argValueString);
					argName.setLength(0);
					argNamespace.setLength(0);
				}
				argValue.setLength(0);
				
				if (tagType == BLOCKSTART) {
                    if (singleTagFlag) {
                        args.put(XMLEvent.KEY_SINGLETON, "true");
                    }
                    
                    if (XML_START.equalsIgnoreCase(tagNameString)) {
                        context = handler.handle(XMLEvent.XML, (History)history.clone(), (Map)args.clone(), context, line, column);
                    } else {
    					history.forward(tagNameString);
    					context = handler.handle(XMLEvent.OPEN, (History)history.clone(), (Map)args.clone(), context, line, column);
    					
    					if (singleTagFlag) {
    						context = handler.handle(XMLEvent.CLOSE, (History)history.clone(), null, context, line, column);
                            history.back(tagNameString);
    					}
                    }

                    singleTagFlag = false;
				}
				
				else { // tagtype == BLOCKEND
					try {
						Object state = null;
						do {
							context = handler.handle(XMLEvent.CLOSE, (History)history.clone(), null, context, line, column);
							state = history.back(tagNameString);
						} while(null != state && !tagNameString.equals(state));
					} catch(IllegalStateException e) {
						throw new IllegalStateException(e.getMessage() + " at line " + line + " column " + column);
					}
				}
				
				tagName.setLength(0);
                namespace.setLength(0);
				tagType = BLOCKSTART;
				status = OUTSIDE;
				realstatus = status;
				args.clear();
				text.setLength(0);
				break;
			
			case OUTSIDE+GREATERTHAN:
			case STARTED+EQUALS:
			case STARTED+LESSTHAN:
			case STARTED+GREATERTHAN:
			case STARTED+ORDINARY:
			case STARTED+QUOTE:
			case STARTED+SEMICOLON:
            case STARTED+COLON:
			case TAGNAME+EQUALS:
			case TAGNAME+ORDINARY:
			case TAGNAME+LESSTHAN:
			case TAGNAME+QUOTE:
			case TAGNAME+SEMICOLON:
			case TAGNAME+EXCLAMATION:
			case TAGSPACE+LESSTHAN:
			case TAGSPACE+EQUALS:
			case TAGSPACE+ORDINARY:
			case TAGSPACE+QUOTE:
            case TAGSPACE+COLON:
			case TAGSPACE+SEMICOLON:
			case TAGSPACE+EXCLAMATION:
			case TAGARG+LESSTHAN:
			case TAGARG+GREATERTHAN:
			case TAGARG+ORDINARY:
			case TAGARG+SLASH:
			case TAGARG+QUOTE:
			case TAGARG+QUERY:
			case TAGARG+SEMICOLON:
			case TAGARG+EXCLAMATION:
			case TAGEQUALS+LESSTHAN:
			case TAGEQUALS+GREATERTHAN:
			case TAGEQUALS+EQUALS:
			case TAGEQUALS+QUERY:
			case TAGEQUALS+SEMICOLON:
            case TAGEQUALS+COLON:
			case TAGEQUALS+EXCLAMATION:
			case TAGARGVAL+LESSTHAN:
			case TAGARGVAL+EQUALS:
			case TAGARGVAL+SEMICOLON:
			case TAGARGVAL+EXCLAMATION:

			    System.err.println("state=" + status + "(" + statusName(status) + ") chartype=" + chartype + "(" + symbolName(chartype));
				throw new UnexpectedCharacterException(c, line, column);
				
			default:
				throw new BadXMLException("Incomplete State Machine: state " + 
					status + ", chartype " + chartype + " not implemented at line " + line + " column " + column);
			}
		}
		
		if (isLax()) {
			String s = trim(text.toString());
			if (s.length() > 0) {
				handler.handle(XMLEvent.TEXT, (History)history.clone(), singleMap(s), context, line, column);
			}
			while (history.depth() > 0) {
				context = handler.handle(XMLEvent.CLOSE, (History)history.clone(), 
				        (Map<String, String>)args.clone(), context, line, column);
				history.back(null);
			}
		}
		
		context = handler.handle(XMLEvent.END, (History)history.clone(), null, context, line, column);
		return context;
	}

	public Object process(Reader in, XMLEventHandler handler, Object context) throws IOException {
		return process(in, handler, new HierarchyHistory<String>(isLax()), context);
	}

	public Object process(Reader in, XMLEventHandler handler) throws IOException {
		return process(in, handler, null);
	}
	
	private Map<String, String> singleMap(String string) {
		Map<String, String> ret = new HashMap<String, String>(1);
		ret.put(XMLEvent.KEY_VALUE, string);
		return ret;
	}

	public boolean isLax() {
		return parameters.containsKey(KEY_LAX);
	}

	public boolean isTrimmed() {
		return parameters.containsKey(KEY_TRIM);
	}

    public boolean isStripped() {
        return parameters.containsKey(KEY_STRIPNS);
    }

    public boolean isEntityEvents() {
        return parameters.containsKey(KEY_ENTITIES);
    }
	
	private String trim(String string) {
		return isTrimmed() ? string.trim() : string;
	}

	private boolean isNameChar(char c) {
		return (Character.isUpperCase(c) || Character.isLowerCase(c) || Character.isDigit(c) 
				|| c=='-' || c=='_' || c==':');
	}
}
