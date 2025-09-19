// CONST 1
mov, r3, 1

/* Multiplies r0 by r1 using repeated addition.
 * Result is stored in r0.
 * Uses r2 as an accumulator and r3 as a constant 1.
 * Assumes r0 and r1 are non-negative integers.
 */










main:
    mov, r0, 100
    mov, r1, 100
    mov, r2, 0
    b, mult


mult:
    cmp, r0, r1
    bge, mult_preloop
    xor, r0, r1, r0
    xor, r1, r0, r1
    xor, r0, r1, r0

    mult_preloop:
        sub, r1, r1, r3

    mult_loop:
        add r2, r0, r2
        sub, r1, r1, r3
        bge, mult_loop

    mult_end:
        xor, r2, r0, r2
        xor, r0, r2, r0
        b, exit

exit:
    b.

