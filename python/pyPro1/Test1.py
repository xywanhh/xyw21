
from functools import reduce

if __name__ == '__main__':
    DIGITS = {'0': 0, '1': 1, '2': 2, '3': 3, '4': 4, '5': 5, '6': 6, '7': 7, '8': 8, '9': 9}

    def mul(L):
        def f1(x, y):
            return x * y
        return reduce(f1, L)

    def mul1(L):
        return reduce(lambda x,y:x*y, L)

    def c1(name):
        def f1(s):
            return s[0:1].upper() + s[1:].lower()
        return map(f1, name)

    # 把字符串'123.456'转换成浮点数123.456
    def c2(s):
        s = s.split('.')
        def char2num(s):
            return DIGITS[s]
        return reduce(lambda x,y:x*10+y, map(char2num, s[0])) \
               + reduce(lambda x,y:x*10+y, map(char2num, s[1]))*0.1**(len(s[1]))

    # 埃氏筛法
    # 先构造一个从3开始的奇数序列：
    def _odd_iter():
        n = 1
        while True:
            n = n + 2
            yield n
    # 定义一个筛选函数
    def _not_divisible(n):
        return lambda x:x%n > 0
    # 定义一个生成器，不断返回下一个素数：
    def primes():
        yield 2
        it = _odd_iter() # 初始序列
        while True:
            n = next(it) # 返回序列的第一个数
            yield n
            it = filter(_not_divisible(n), it) # 构造新序列
    # 打印1000以内的素数:
    for n in primes():
        if n < 1000:
            print(n)
        else:
            break

    # 筛选出回数
    def is_palindrome(n):
        n = str(n)
        if n == n[::-1]:
            return n


    # 测试:
    output = filter(is_palindrome, range(1, 100))
    # print('1~1000:', list(output))
    if list(filter(is_palindrome, range(1, 100))) == [1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 22, 33, 44, 55, 66, 77, 88, 99]:
        print('测试成功!')
    else:
        print('测试失败!')

    # print(mul([3, 5, 7, 9]))
    # print(mul1([3, 5, 7, 9]))
    # print(list(c1(['adam', 'LISA', 'barT'])))
    # print(c2('123.456'))