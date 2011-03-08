dummy.value=ugh
one$: tests.spec.DummyWithoutInit
two$: tests.spec.DummyWithInit
three$: tests.spec.DummyWithOtherInit
other.value=whatever
four$ = tests.spec.DummyWithConstructor hello there
five$ = tests.spec.DummyWithBoth other.value