	.file	"test2.c"
	.text
	.globl	skipSpace
	.def	skipSpace;	.scl	2;	.type	32;	.endef
	.seh_proc	skipSpace
skipSpace:
	pushq	%rbp
	.seh_pushreg	%rbp
	pushq	%rbx
	.seh_pushreg	%rbx
	subq	$56, %rsp
	.seh_stackalloc	56
	leaq	128(%rsp), %rbp
	.seh_setframe	%rbp, 128
	.seh_endprologue
	movq	%rcx, -48(%rbp)
	movq	%rdx, -40(%rbp)
	movl	$0, -84(%rbp)
	jmp	.L2
.L4:
	addl	$1, -84(%rbp)
.L2:
	movl	-84(%rbp), %eax
	movslq	%eax, %rbx
	movq	-48(%rbp), %rcx
	call	strlen
	cmpq	%rax, %rbx
	jnb	.L3
	movq	-40(%rbp), %rax
	movl	(%rax), %edx
	movl	-84(%rbp), %eax
	addl	%edx, %eax
	movslq	%eax, %rdx
	movq	-48(%rbp), %rax
	addq	%rdx, %rax
	movzbl	(%rax), %eax
	cmpb	$32, %al
	je	.L4
.L3:
	movq	-40(%rbp), %rax
	movl	(%rax), %edx
	movl	-84(%rbp), %eax
	addl	%eax, %edx
	movq	-40(%rbp), %rax
	movl	%edx, (%rax)
	nop
	addq	$56, %rsp
	popq	%rbx
	popq	%rbp
	ret
	.seh_endproc
	.globl	isValidJson
	.def	isValidJson;	.scl	2;	.type	32;	.endef
	.seh_proc	isValidJson
isValidJson:
	pushq	%rbp
	.seh_pushreg	%rbp
	pushq	%rbx
	.seh_pushreg	%rbx
	subq	$88, %rsp
	.seh_stackalloc	88
	leaq	128(%rsp), %rbp
	.seh_setframe	%rbp, 128
	.seh_endprologue
	movq	%rcx, -16(%rbp)
	leaq	-80(%rbp), %rax
	movl	$10, %edx
	movq	%rax, %rcx
	call	init
	movl	$0, -84(%rbp)
	jmp	.L6
.L32:
	leaq	-84(%rbp), %rax
	movq	%rax, %rdx
	movq	-16(%rbp), %rcx
	call	skipSpace
	movl	-84(%rbp), %eax
	movslq	%eax, %rdx
	movq	-16(%rbp), %rax
	addq	%rdx, %rax
	movzbl	(%rax), %eax
	cmpb	$123, %al
	jne	.L7
	leaq	-80(%rbp), %rax
	movl	$1, %edx
	movq	%rax, %rcx
	call	push
	movl	-84(%rbp), %eax
	addl	$1, %eax
	movl	%eax, -84(%rbp)
.L7:
	leaq	-84(%rbp), %rax
	movq	%rax, %rdx
	movq	-16(%rbp), %rcx
	call	skipSpace
	movl	-84(%rbp), %eax
	movslq	%eax, %rdx
	movq	-16(%rbp), %rax
	addq	%rdx, %rax
	movzbl	(%rax), %eax
	cmpb	$34, %al
	jne	.L8
	movl	-84(%rbp), %eax
	addl	$1, %eax
	movl	%eax, -84(%rbp)
	jmp	.L9
.L11:
	movl	-84(%rbp), %eax
	addl	$1, %eax
	movl	%eax, -84(%rbp)
.L9:
	movl	-84(%rbp), %eax
	movslq	%eax, %rdx
	movq	-16(%rbp), %rax
	addq	%rdx, %rax
	movzbl	(%rax), %eax
	cmpb	$34, %al
	je	.L10
	movl	-84(%rbp), %eax
	movslq	%eax, %rdx
	movq	-16(%rbp), %rax
	addq	%rdx, %rax
	movzbl	(%rax), %eax
	testb	%al, %al
	jne	.L11
.L10:
	movl	-84(%rbp), %eax
	movslq	%eax, %rdx
	movq	-16(%rbp), %rax
	addq	%rdx, %rax
	movzbl	(%rax), %eax
	cmpb	$34, %al
	jne	.L12
	movl	-84(%rbp), %eax
	addl	$1, %eax
	movl	%eax, -84(%rbp)
.L12:
	leaq	-84(%rbp), %rax
	movq	%rax, %rdx
	movq	-16(%rbp), %rcx
	call	skipSpace
	movl	-84(%rbp), %eax
	movslq	%eax, %rdx
	movq	-16(%rbp), %rax
	addq	%rdx, %rax
	movzbl	(%rax), %eax
	cmpb	$58, %al
	jne	.L13
	movl	-84(%rbp), %eax
	addl	$1, %eax
	movl	%eax, -84(%rbp)
	leaq	-84(%rbp), %rax
	movq	%rax, %rdx
	movq	-16(%rbp), %rcx
	call	skipSpace
	movl	-84(%rbp), %eax
	movslq	%eax, %rdx
	movq	-16(%rbp), %rax
	addq	%rdx, %rax
	movzbl	(%rax), %eax
	cmpb	$123, %al
	jne	.L16
	jmp	.L37
.L13:
	movl	$0, %eax
	jmp	.L36
.L37:
	movl	-84(%rbp), %eax
	subl	$1, %eax
	movl	%eax, -84(%rbp)
	jmp	.L17
.L16:
	movl	-84(%rbp), %eax
	movslq	%eax, %rdx
	movq	-16(%rbp), %rax
	addq	%rdx, %rax
	movzbl	(%rax), %eax
	cmpb	$47, %al
	jle	.L18
	movl	-84(%rbp), %eax
	movslq	%eax, %rdx
	movq	-16(%rbp), %rax
	addq	%rdx, %rax
	movzbl	(%rax), %eax
	cmpb	$57, %al
	jg	.L18
	jmp	.L19
.L21:
	movl	-84(%rbp), %eax
	addl	$1, %eax
	movl	%eax, -84(%rbp)
.L19:
	movl	-84(%rbp), %eax
	movslq	%eax, %rdx
	movq	-16(%rbp), %rax
	addq	%rdx, %rax
	movzbl	(%rax), %eax
	cmpb	$47, %al
	jle	.L39
	movl	-84(%rbp), %eax
	movslq	%eax, %rdx
	movq	-16(%rbp), %rax
	addq	%rdx, %rax
	movzbl	(%rax), %eax
	cmpb	$57, %al
	jle	.L21
	jmp	.L39
.L18:
	movl	$0, %eax
	jmp	.L36
.L39:
	nop
	movl	-84(%rbp), %eax
	movslq	%eax, %rdx
	movq	-16(%rbp), %rax
	addq	%rdx, %rax
	movzbl	(%rax), %eax
	cmpb	$125, %al
	jne	.L23
	leaq	-80(%rbp), %rax
	movq	%rax, %rcx
	call	isempty
	testl	%eax, %eax
	je	.L24
	movl	$0, %eax
	jmp	.L36
.L24:
	leaq	-80(%rbp), %rax
	movq	%rax, %rcx
	call	pop
	jmp	.L17
.L23:
	movl	$0, %eax
	jmp	.L36
.L17:
	leaq	-84(%rbp), %rax
	movq	%rax, %rdx
	movq	-16(%rbp), %rcx
	call	skipSpace
	jmp	.L25
.L8:
	movl	-84(%rbp), %eax
	movslq	%eax, %rdx
	movq	-16(%rbp), %rax
	addq	%rdx, %rax
	movzbl	(%rax), %eax
	cmpb	$125, %al
	jne	.L26
	jmp	.L27
.L29:
	leaq	-80(%rbp), %rax
	movq	%rax, %rcx
	call	isempty
	testl	%eax, %eax
	je	.L28
	movl	$0, %eax
	jmp	.L36
.L28:
	leaq	-80(%rbp), %rax
	movq	%rax, %rcx
	call	pop
	movl	-84(%rbp), %eax
	addl	$1, %eax
	movl	%eax, -84(%rbp)
	leaq	-84(%rbp), %rax
	movq	%rax, %rdx
	movq	-16(%rbp), %rcx
	call	skipSpace
.L27:
	movl	-84(%rbp), %eax
	movslq	%eax, %rdx
	movq	-16(%rbp), %rax
	addq	%rdx, %rax
	movzbl	(%rax), %eax
	cmpb	$125, %al
	je	.L29
	jmp	.L25
.L26:
	movl	-84(%rbp), %eax
	movslq	%eax, %rdx
	movq	-16(%rbp), %rax
	addq	%rdx, %rax
	movzbl	(%rax), %eax
	cmpb	$123, %al
	jne	.L30
	movl	-84(%rbp), %eax
	subl	$1, %eax
	movl	%eax, -84(%rbp)
	jmp	.L25
.L30:
	movl	$0, %eax
	jmp	.L36
.L25:
	leaq	-84(%rbp), %rax
	movq	%rax, %rdx
	movq	-16(%rbp), %rcx
	call	skipSpace
	movl	-84(%rbp), %eax
	addl	$1, %eax
	movl	%eax, -84(%rbp)
.L6:
	movl	-84(%rbp), %eax
	movslq	%eax, %rbx
	movq	-16(%rbp), %rcx
	call	strlen
	cmpq	%rax, %rbx
	jnb	.L31
	movl	-84(%rbp), %eax
	movslq	%eax, %rdx
	movq	-16(%rbp), %rax
	addq	%rdx, %rax
	movzbl	(%rax), %eax
	testb	%al, %al
	jne	.L32
.L31:
	leaq	-80(%rbp), %rax
	movq	%rax, %rcx
	call	isempty
	testl	%eax, %eax
	je	.L38
	movl	$1, %eax
	jmp	.L36
.L38:
	movl	$0, %eax
.L36:
	addq	$88, %rsp
	popq	%rbx
	popq	%rbp
	ret
	.seh_endproc
	.def	__main;	.scl	2;	.type	32;	.endef
	.section .rdata,"dr"
.LC0:
	.ascii "Valid test\0"
.LC1:
	.ascii "pass\0"
.LC2:
	.ascii "fail\0"
.LC3:
	.ascii "%-50s: - %s\12\0"
.LC4:
	.ascii "Invalid test\0"
.LC5:
	.ascii "%s: - %s\12\0"
	.text
	.globl	main
	.def	main;	.scl	2;	.type	32;	.endef
	.seh_proc	main
main:
	pushq	%rbp
	.seh_pushreg	%rbp
	subq	$416, %rsp
	.seh_stackalloc	416
	leaq	128(%rsp), %rbp
	.seh_setframe	%rbp, 128
	.seh_endprologue
	call	__main
	leaq	256(%rbp), %rax
	movl	$100, %edx
	movq	%rax, %rcx
	call	init
	leaq	.LC0(%rip), %rcx
	call	puts
	movw	$32123, 253(%rbp)
	movb	$0, 255(%rbp)
	leaq	253(%rbp), %rax
	movq	%rax, %rcx
	call	isValidJson
	cmpl	$1, %eax
	jne	.L41
	leaq	.LC1(%rip), %rax
	jmp	.L42
.L41:
	leaq	.LC2(%rip), %rax
.L42:
	leaq	253(%rbp), %rdx
	movq	%rax, %r8
	leaq	.LC3(%rip), %rcx
	call	printf
	movabsq	$3547185466755326587, %rax
	movq	%rax, 241(%rbp)
	movl	$8205106, 249(%rbp)
	leaq	241(%rbp), %rax
	movq	%rax, %rcx
	call	isValidJson
	cmpl	$1, %eax
	jne	.L43
	leaq	.LC1(%rip), %rax
	jmp	.L44
.L43:
	leaq	.LC2(%rip), %rax
.L44:
	leaq	241(%rbp), %rdx
	movq	%rax, %r8
	leaq	.LC3(%rip), %rcx
	call	printf
	movabsq	$8879447425561993851, %rax
	movq	%rax, 208(%rbp)
	movabsq	$4189033114873718818, %rax
	movq	%rax, 216(%rbp)
	movl	$2100507185, 224(%rbp)
	movw	$125, 228(%rbp)
	leaq	208(%rbp), %rax
	movq	%rax, %rcx
	call	isValidJson
	cmpl	$1, %eax
	jne	.L45
	leaq	.LC1(%rip), %rax
	jmp	.L46
.L45:
	leaq	.LC2(%rip), %rax
.L46:
	leaq	208(%rbp), %rdx
	movq	%rax, %r8
	leaq	.LC3(%rip), %rcx
	call	printf
	movabsq	$8879447425561993851, %rax
	movq	%rax, 195(%rbp)
	movl	$2105376123, 203(%rbp)
	movb	$0, 207(%rbp)
	leaq	195(%rbp), %rax
	movq	%rax, %rcx
	call	isValidJson
	cmpl	$1, %eax
	jne	.L47
	leaq	.LC1(%rip), %rax
	jmp	.L48
.L47:
	leaq	.LC2(%rip), %rax
.L48:
	leaq	195(%rbp), %rdx
	movq	%rax, %r8
	leaq	.LC3(%rip), %rcx
	call	printf
	movabsq	$8879447425561993851, %rax
	movq	%rax, 160(%rbp)
	movabsq	$8879447425561993851, %rax
	movq	%rax, 168(%rbp)
	movabsq	$9042521277212025122, %rax
	movq	%rax, 176(%rbp)
	movw	$125, 184(%rbp)
	leaq	160(%rbp), %rax
	movq	%rax, %rcx
	call	isValidJson
	cmpl	$1, %eax
	jne	.L49
	leaq	.LC1(%rip), %rax
	jmp	.L50
.L49:
	leaq	.LC2(%rip), %rax
.L50:
	leaq	160(%rbp), %rdx
	movq	%rax, %r8
	leaq	.LC3(%rip), %rcx
	call	printf
	movabsq	$8879447425561993851, %rax
	movq	%rax, 128(%rbp)
	movabsq	$2314888018716074619, %rax
	movq	%rax, 136(%rbp)
	movabsq	$4189017404132171808, %rax
	movq	%rax, 144(%rbp)
	movl	$2105376049, 152(%rbp)
	movw	$125, 156(%rbp)
	leaq	128(%rbp), %rax
	movq	%rax, %rcx
	call	isValidJson
	cmpl	$1, %eax
	jne	.L51
	leaq	.LC1(%rip), %rax
	jmp	.L52
.L51:
	leaq	.LC2(%rip), %rax
.L52:
	leaq	128(%rbp), %rdx
	movq	%rax, %r8
	leaq	.LC3(%rip), %rcx
	call	printf
	movabsq	$8879447425561993851, %rax
	movq	%rax, 96(%rbp)
	movabsq	$2314888018716074619, %rax
	movq	%rax, 104(%rbp)
	movabsq	$4189017404132171808, %rax
	movq	%rax, 112(%rbp)
	movl	$2105376049, 120(%rbp)
	movw	$125, 124(%rbp)
	leaq	96(%rbp), %rax
	movq	%rax, %rcx
	call	isValidJson
	cmpl	$1, %eax
	jne	.L53
	leaq	.LC1(%rip), %rax
	jmp	.L54
.L53:
	leaq	.LC2(%rip), %rax
.L54:
	leaq	96(%rbp), %rdx
	movq	%rax, %r8
	leaq	.LC3(%rip), %rcx
	call	printf
	movabsq	$7161393156668268667, %rax
	movq	%rax, 48(%rbp)
	movabsq	$7161393158200965666, %rax
	movq	%rax, 56(%rbp)
	movabsq	$2484643529077301282, %rax
	movq	%rax, 64(%rbp)
	movabsq	$9042521603480101473, %rax
	movq	%rax, 72(%rbp)
	movb	$0, 80(%rbp)
	leaq	48(%rbp), %rax
	movq	%rax, %rcx
	call	isValidJson
	cmpl	$1, %eax
	jne	.L55
	leaq	.LC1(%rip), %rax
	jmp	.L56
.L55:
	leaq	.LC2(%rip), %rax
.L56:
	leaq	48(%rbp), %rdx
	movq	%rax, %r8
	leaq	.LC3(%rip), %rcx
	call	printf
	movabsq	$2314888018716074619, %rax
	movq	%rax, 0(%rbp)
	movabsq	$6999292540073549856, %rax
	movq	%rax, 8(%rbp)
	movabsq	$4188382975804728162, %rax
	movq	%rax, 16(%rbp)
	movabsq	$6999192093237190779, %rax
	movq	%rax, 24(%rbp)
	movabsq	$35322350013594146, %rax
	movq	%rax, 32(%rbp)
	movq	%rbp, %rax
	movq	%rax, %rcx
	call	isValidJson
	cmpl	$1, %eax
	jne	.L57
	leaq	.LC1(%rip), %rax
	jmp	.L58
.L57:
	leaq	.LC2(%rip), %rax
.L58:
	movq	%rbp, %rdx
	movq	%rax, %r8
	leaq	.LC3(%rip), %rcx
	call	printf
	movl	$10, %ecx
	call	putchar
	leaq	.LC4(%rip), %rcx
	call	puts
	movw	$8315, -3(%rbp)
	movb	$0, -1(%rbp)
	leaq	-3(%rbp), %rax
	movq	%rax, %rcx
	call	isValidJson
	testl	%eax, %eax
	jne	.L59
	leaq	.LC1(%rip), %rax
	jmp	.L60
.L59:
	leaq	.LC2(%rip), %rax
.L60:
	leaq	-3(%rbp), %rdx
	movq	%rax, %r8
	leaq	.LC5(%rip), %rcx
	call	printf
	movabsq	$3616736174281925243, %rax
	movq	%rax, -14(%rbp)
	movw	$32051, -6(%rbp)
	movb	$0, -4(%rbp)
	leaq	-14(%rbp), %rax
	movq	%rax, %rcx
	call	isValidJson
	testl	%eax, %eax
	jne	.L61
	leaq	.LC1(%rip), %rax
	jmp	.L62
.L61:
	leaq	.LC2(%rip), %rax
.L62:
	leaq	-14(%rbp), %rdx
	movq	%rax, %r8
	leaq	.LC5(%rip), %rcx
	call	printf
	movabsq	$8879447425561993851, %rax
	movq	%rax, -48(%rbp)
	movabsq	$4189033114873718818, %rax
	movq	%rax, -40(%rbp)
	movl	$2100507185, -32(%rbp)
	movb	$0, -28(%rbp)
	leaq	-48(%rbp), %rax
	movq	%rax, %rcx
	call	isValidJson
	testl	%eax, %eax
	jne	.L63
	leaq	.LC1(%rip), %rax
	jmp	.L64
.L63:
	leaq	.LC2(%rip), %rax
.L64:
	leaq	-48(%rbp), %rdx
	movq	%rax, %r8
	leaq	.LC5(%rip), %rcx
	call	printf
	movabsq	$8879447425561993851, %rax
	movq	%rax, -60(%rbp)
	movl	$8224125, -52(%rbp)
	leaq	-60(%rbp), %rax
	movq	%rax, %rcx
	call	isValidJson
	testl	%eax, %eax
	jne	.L65
	leaq	.LC1(%rip), %rax
	jmp	.L66
.L65:
	leaq	.LC2(%rip), %rax
.L66:
	leaq	-60(%rbp), %rdx
	movq	%rax, %r8
	leaq	.LC5(%rip), %rcx
	call	printf
	movabsq	$8879447425561993851, %rax
	movq	%rax, -96(%rbp)
	movabsq	$2466321556186407547, %rax
	movq	%rax, -88(%rbp)
	movabsq	$9042521603480101473, %rax
	movq	%rax, -80(%rbp)
	movb	$0, -72(%rbp)
	leaq	-96(%rbp), %rax
	movq	%rax, %rcx
	call	isValidJson
	testl	%eax, %eax
	jne	.L67
	leaq	.LC1(%rip), %rax
	jmp	.L68
.L67:
	leaq	.LC2(%rip), %rax
.L68:
	leaq	-96(%rbp), %rdx
	movq	%rax, %r8
	leaq	.LC5(%rip), %rcx
	call	printf
	movl	$0, %eax
	addq	$416, %rsp
	popq	%rbp
	ret
	.seh_endproc
	.ident	"GCC: (GNU) 6.4.0"
	.def	strlen;	.scl	2;	.type	32;	.endef
	.def	init;	.scl	2;	.type	32;	.endef
	.def	push;	.scl	2;	.type	32;	.endef
	.def	isempty;	.scl	2;	.type	32;	.endef
	.def	pop;	.scl	2;	.type	32;	.endef
	.def	puts;	.scl	2;	.type	32;	.endef
	.def	printf;	.scl	2;	.type	32;	.endef
	.def	putchar;	.scl	2;	.type	32;	.endef
