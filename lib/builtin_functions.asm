





default rel

global _toString
global _getInt
global _getString
global _stringConcat
global _ord
global _subString
global _stringEQ
global _stringLT
global _printInt
global _printlnInt
global _print

extern fputs
extern stdout
extern __printf_chk
extern strcmp
extern strncpy
extern memcpy
extern strlen
extern __isoc99_scanf
extern __stack_chk_fail
extern malloc
extern _GLOBAL_OFFSET_TABLE_


SECTION .text

_toString:
        push    r15
        push    r14
        push    r13
        push    r12
        push    rbp
        push    rbx
        sub     rsp, 40


        mov     rax, qword [fs:abs 28H]
        mov     qword [rsp+18H], rax
        xor     eax, eax
        test    rdi, rdi
        je      L_006
        mov     rax, rdi
        mov     r13, rdi
        mov     r12, rdi
        sar     rax, 63
        shr     r13, 63
        mov     rcx, rax
        xor     rcx, rdi
        mov     rdi, qword 0CCCCCCCCCCCCCCCDH
        sub     rcx, rax
        xor     eax, eax




ALIGN   8
L_001:  lea     ebp, [rax+1H]
        movsx   rsi, ax
        mov     rax, rcx
        mul     rdi
        shr     rdx, 3
        lea     rax, [rdx+rdx*4]
        add     rax, rax
        sub     rcx, rax
        test    rdx, rdx
        mov     eax, ebp
        mov     word [rsp+rsi*2], cx
        mov     rcx, rdx
        jnz     L_001
        movsx   ebx, bp
        movzx   r14d, r13b
        lea     r15d, [r14+rbx]
        lea     edi, [r15+1H]
        movsxd  rdi, edi
        call    malloc
        test    r12, r12
        js      L_005
L_002:  test    bp, bp
        jle     L_003
        lea     edx, [rbx-1H]
        movsxd  rdx, edx
        movzx   edx, word [rsp+rdx*2]
        add     edx, 48
        cmp     bp, 1
        mov     byte [rax+r13], dl
        je      L_003
        lea     ecx, [rbx-2H]
        lea     edx, [r14+1H]
        movsxd  rcx, ecx
        movsxd  rdx, edx
        movzx   ecx, word [rsp+rcx*2]
        add     ecx, 48
        cmp     bp, 2
        mov     byte [rax+rdx], cl
        je      L_003
        lea     ecx, [rbx-3H]
        lea     edx, [r14+2H]
        movsxd  rcx, ecx
        movsxd  rdx, edx
        movzx   ecx, word [rsp+rcx*2]
        add     ecx, 48
        cmp     bp, 3
        mov     byte [rax+rdx], cl
        je      L_003
        lea     ecx, [rbx-4H]
        lea     edx, [r14+3H]
        movsxd  rcx, ecx
        movsxd  rdx, edx
        movzx   ecx, word [rsp+rcx*2]
        add     ecx, 48
        cmp     bp, 4
        mov     byte [rax+rdx], cl
        je      L_003
        lea     ecx, [rbx-5H]
        lea     edx, [r14+4H]
        movsxd  rcx, ecx
        movsxd  rdx, edx
        movzx   ecx, word [rsp+rcx*2]
        add     ecx, 48
        cmp     bp, 5
        mov     byte [rax+rdx], cl
        je      L_003
        lea     ecx, [rbx-6H]
        lea     edx, [r14+5H]
        movsxd  rcx, ecx
        movsxd  rdx, edx
        movzx   ecx, word [rsp+rcx*2]
        add     ecx, 48
        cmp     bp, 6
        mov     byte [rax+rdx], cl
        jz      L_003
        lea     ecx, [rbx-7H]
        lea     edx, [r14+6H]
        movsxd  rcx, ecx
        movsxd  rdx, edx
        movzx   ecx, word [rsp+rcx*2]
        add     ecx, 48
        cmp     bp, 7
        mov     byte [rax+rdx], cl
        jz      L_003
        lea     ecx, [rbx-8H]
        lea     edx, [r14+7H]
        movsxd  rcx, ecx
        movsxd  rdx, edx
        movzx   ecx, word [rsp+rcx*2]
        add     ecx, 48
        cmp     bp, 8
        mov     byte [rax+rdx], cl
        jz      L_003
        lea     ecx, [rbx-9H]
        lea     edx, [r14+8H]
        movsxd  rcx, ecx
        movsxd  rdx, edx
        movzx   ecx, word [rsp+rcx*2]
        add     ecx, 48
        cmp     bp, 9
        mov     byte [rax+rdx], cl
        jz      L_003
        sub     ebx, 10
        add     r14d, 9
        movsxd  rbx, ebx
        movsxd  r14, r14d
        movzx   edx, word [rsp+rbx*2]
        add     edx, 48
        mov     byte [rax+r14], dl




ALIGN   8
L_003:  movsxd  r15, r15d
        mov     byte [rax+r15], 0
L_004:  mov     rbx, qword [rsp+18H]


        xor     rbx, qword [fs:abs 28H]
        jnz     L_007
        add     rsp, 40
        pop     rbx
        pop     rbp
        pop     r12
        pop     r13
        pop     r14
        pop     r15
        ret






ALIGN   16
L_005:  mov     byte [rax], 45
        jmp     L_002





ALIGN   16
L_006:  mov     edi, 2
        call    malloc
        mov     edx, 48
        mov     word [rax], dx
        jmp     L_004


L_007:
        call    __stack_chk_fail




ALIGN   8

_getInt:
        sub     rsp, 24
        lea     rdi, [rel LC0]


        mov     rax, qword [fs:abs 28H]
        mov     qword [rsp+8H], rax
        xor     eax, eax
        mov     rsi, rsp
        call    __isoc99_scanf
        mov     rdx, qword [rsp+8H]


        xor     rdx, qword [fs:abs 28H]
        mov     rax, qword [rsp]
        jnz     L_008
        add     rsp, 24
        ret

L_008:
        call    __stack_chk_fail





ALIGN   16

_getString:
        push    rbx
        mov     edi, 257
        call    malloc
        lea     rdi, [rel LC1]
        mov     rbx, rax
        mov     rsi, rax
        xor     eax, eax
        call    __isoc99_scanf
        mov     rax, rbx
        pop     rbx
        ret






ALIGN   16

_stringConcat:
        push    r14
        push    r13
        mov     r13, rsi
        push    r12
        push    rbp
        mov     r14, rdi
        push    rbx
        call    strlen
        mov     rdi, r13
        mov     rbx, rax
        call    strlen
        lea     edi, [rbx+rax+1H]
        mov     rbp, rax
        movsxd  rdi, edi
        call    malloc
        mov     rdx, rbx
        mov     r12, rax
        mov     rsi, r14
        mov     rdi, rax
        call    memcpy
        lea     rdi, [r12+rbx]
        lea     rdx, [rbp+1H]
        mov     rsi, r13
        call    memcpy
        pop     rbx
        mov     rax, r12
        pop     rbp
        pop     r12
        pop     r13
        pop     r14
        ret






ALIGN   8

_ord:
        movsx   rax, byte [rsi+rdi]
        ret







ALIGN   16

_subString:
        push    r12
        push    rbp
        mov     r12, rdi
        push    rbx
        mov     rbx, rsi
        mov     rbp, rdx
        sub     rbx, rdi
        lea     rdi, [rbx+2H]
        call    malloc
        lea     rsi, [rbp+r12]
        lea     rdx, [rbx+1H]
        mov     rdi, rax
        call    strncpy
        mov     byte [rax+rbx+1H], 0
        pop     rbx
        pop     rbp
        pop     r12
        ret







ALIGN   16

_stringEQ:
        sub     rsp, 8
        call    strcmp
        test    eax, eax
        sete    al
        add     rsp, 8
        movzx   eax, al
        ret







ALIGN   16

_stringLT:
        sub     rsp, 8
        call    strcmp
        cdqe
        add     rsp, 8
        shr     rax, 63
        ret







ALIGN   16

_printInt:
        lea     rsi, [rel LC0]
        sub     rsp, 8
        mov     rdx, rdi
        xor     eax, eax
        mov     edi, 1
        call    __printf_chk
        add     rsp, 8
        ret


        nop

ALIGN   16
_printlnInt:
        lea     rsi, [rel LC2]
        sub     rsp, 8
        mov     rdx, rdi
        xor     eax, eax
        mov     edi, 1
        call    __printf_chk
        add     rsp, 8
        ret


        nop

ALIGN   16
_print:
        sub     rsp, 8
        mov     rsi, qword [rel stdout]
        call    fputs
        add     rsp, 8
        ret



SECTION .data


SECTION .bss


SECTION .rodata.str1.1

LC0:
        db 25H, 6CH, 6CH, 64H, 00H

LC1:
        db 25H, 73H, 00H

LC2:
        db 25H, 6CH, 6CH, 64H, 0AH, 00H


