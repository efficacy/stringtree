2011-03-09 Frank Carver
-----------------------
Stringtree is a general purpose Java library containing a selection of useful code.

Highlights include:

Stringtree Templater
The template engine which powers the Mojasef server framework, but is also powerful and flexible enough 
for any kind of text or data substitution.

Stringtree JSON
A small lean and fast memory-based fully-compliant JSON reader and writer, with optional validation. 
Especially suitable for import, export and transfer of arbitrary models as JSON.  

Stringtree JSON Events
A small lean and fast event-based fully-compliant JSON reader.
Especially suitable for efficient "cherry-picking" of partial data without needing to load a whole model

Stringtree XML
A small lean and fast memory-based XML reader(parser) and writer, with optional validation. 
Especially suitable for import, export and transfer of arbitrary models as XML.  

Stringtree XML Events
A small lean and fast event-based fully-compliant XML parser.
Especially suitable for efficient "cherry-picking" of partial data without needing to load a whole model

Stringtree JMS
An ultra-lightweight in-memory implementation of JMS. Especially suitable for unit-testing JMS 
systems and acting as a thin link layer between separable components.

Stringtree Juicer
A powerful and highly-configurable text processing pipeline, used for many years to power the page generation
at the high traffic JavaRanch FAQ website ( http://www.coderanch.com/how-to/ ) 

Stringtree Workflow
A simple but deceptively powerful internal workflow engine, able to manage state, transitions and side-effects
of arbitrarily complex models


The Stringtree source was originally hosted with sourceforge - initially in CVS, then subversion at 
https://stringtree.svn.sourceforge.net/svnroot/stringtree The current public master version of the code is 
hosted with github at git://github.com/efficacy/stringtree.git
 
As with other Stringtree projects, Stringtree is dual licensed. You may use it under the terms of either

* LGPL 3.0  http://www.gnu.org/licenses/lgpl.html
* Apache licence 2.0  http://www.apache.org/licenses/LICENSE-2.0.html
 