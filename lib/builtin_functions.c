#include <stdio.h>
#include <string.h>
#include <stdlib.h>

char * _toString(long long v) {
//printf("tostring %lld\n", v);
    if (v == 0){
        char *r = (char *)malloc(sizeof(char) * 2);
        r[0] = '0'; r[1] = '\0';
        return r;
    }
    short digits[10];
    short neg = v < 0;
    if (neg) v=-v;
    short len = 0;
    while (v>0){
        digits[len++] = v%10;
        v/=10;
    }
    char *r = (char *)malloc(sizeof(char) * (len + neg + 1));
    short p = 0;
    if (neg) r[0] = '-';
    while (p < len){
        r[p + neg] = digits[len - p - 1] + '0';
        ++p;
    }
    r[len + neg] = '\0';
    return r;
}

long long _getInt(){
    long long a;
    scanf("%lld",&a);
    int c;
    //while ( (c = getchar()) != '\n' && c != EOF && c != ' ') { }
    return a;
}

char * _getString(){
    char * buffer = (char *)malloc(sizeof(char) * 257);
//    int c;
//    while ((c=getchar())=='\n' && c == ' ');
//    char *p= buffer;
//    *(p++) = c;
//    while ((c=getchar())!='\n') *(p++) = c;
//    *p = '\0';
    scanf("%s",buffer);
    return buffer;
}

char * _stringConcat(char * s1, char * s2){
    int len1 = strlen(s1);
    int len2 = strlen(s2);
    int len = len1 + len2;
    char * s = (char *)malloc(sizeof(char) * (len + 1));
    strcpy(s, s1);
    strcat(s, s2);
    return s;
}

long long _ord(long long x, char * s){
//puts(s);
    return (long long)s[x];
}

char * _subString(long long l, long long r, char * s){
    char * s1 = (char *)malloc(sizeof(char) * (r-l+2));
    strncpy(s1, s + l , r - l + 1);
    s1[r-l+1] = '\0';
    return s1;
}


long long _stringEQ(char *a, char *b){
    return strcmp(a,b) == 0;
}

long long _stringLT(char *a, char *b){
    return strcmp(a,b) < 0;
}

void * _printInt(long long x){
    printf("%lld", x);
}

void * _printlnInt(long long x){
    printf("%lld\n", x);
}

void * _print(char *s){
    fputs(s, stdout);
}