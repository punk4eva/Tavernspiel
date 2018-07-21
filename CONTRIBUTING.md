JAVADOC GUIDELINES:

1) Always write javadocs for class, enum, annotation and interface declaration.
2) Only class declarations require @author tags.
3) Always write javadocs for initializers.
4) You may skip method javadocs if:
    -They are inaccessible outside of the class.
    -They are method declarations in an interface.
    -They override or implement from a supertype or interface unless the method does something completely different to its ancestor (which shouldn't happen anyway).
    -They function to extend access privelages to a different method.
