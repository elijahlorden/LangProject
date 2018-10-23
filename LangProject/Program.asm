.import #import from the global symbol table
exampleImport:otherFile.otherSubroutine

.data #variable declaration
byte: .byte 110,'a','b','c' #an underscore prefixing the datatype will read the provided values as unsigned
word: .word 35
sword: .sword 10
string1: .ascii "Hello World" #ascii is not terminated
string2: .asciiz "Hello World" #asciiz is terminated with a null character
charArray: .byte 'H','e','l','l','o'
byteArray: .byte 1,2,3,4,5
block: .block 10 #allocate 16 bytes initialized to 0

.text #code

main:
call exampleImport
call subroutine
ldi $r1 4
add $r0 $r0 $r1
ret
subroutine:
ldi $r0 1
ret

.export #export to the global symbol table
program.entrypoint:main
Program.subroutine:subroutine
