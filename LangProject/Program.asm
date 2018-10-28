.data #variable/constant declaration
byte: ._byte 110,'a','b','c' #an underscore prefixing the datatype will read the provided values as unsigned
word: .word 35
sword: .sword 9
string1: .ascii "Hello World" #ascii is not terminated
string2: .asciiz "Hello World" #asciiz is terminated with a null character
charArray: .byte 'H','e','l','l','o'
byteArray: .byte 1,2,3,4,5
block: .block 16 #allocate 16 bytes initialized to 0

const1: .const 1234567 #constants are assigned a data width by the assembler

.text #code

ldi $r0 1


