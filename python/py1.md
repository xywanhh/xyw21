# 1 简介

动态类型语言
弱类型

py脚本运行
```
1. 命令行交互式
$python
IPython

2. 解释器调用
$python3 ../1.py

3. 脚本内指定运行环境
#!/usr/bin/env python3
print('hello')
$./1.py

```

输入/输出
```
print 'a','b',11
print("aa","bb",'cc')
print(1+2)

a = input()
print(a)
b = input("please enter your name: ")
print("hello ", b)
```


# 2 基础

4个空格缩进
大小写敏感

## 2.1 类型和变量

```
整数
1, 2, -100, 0
十六进制用0x前缀和0-9，a-f表示 0xff00，0xa5b4c3d2

允许在数字中间以_分隔
10_000_000_000 == 10000000000
十六进制数也可以写成  0xa1b2_c3d4

有两种除法：
>>> 10 / 3
3.3333333333333335
>>> 9 / 3
3.0
//，称为地板除，只取结果的整数部分
>>> 10 // 3
3
>>> 10 % 3
1

注意：
Python的整数没有大小限制
浮点数也没有大小限制，但是超出一定范围就直接表示为inf（无限大）。


浮点数
1.23，-0.9

科学计数法表示，把10用e替代
1.23x10^9 == 1.23e9 == 12.3e8
0.000012 == 1.2e-5

注意：
在python中，由于整数和浮点数在计算机内部存储的方式是不同的，整数运算永远是精确的（除法难道也是精确的？是的！），而浮点数运算则可能会有四舍五入的误差。
```

```
字符串
'aa'或"aa"或"""aaa"""或'''aa'''
(前面都可以加上r使用)

转义
'I\'m \"OK\"!'
\n表示换行，\t表示制表符，字符\本身也要转义，所以\\表示的字符就是\

用r''表示''内部的字符串默认不转义
print('\\\t\\')
\       \
print(r'\\\t\\')
\\\t\\

```

```
布尔值
True、False
and、or、not

空值
None

None不能理解为0，因为0是有意义的，而None是一个特殊的空值。

```

变量
变量名必须是大小写英文、数字和_的组合，且不能用数字开头
```
可以把任意数据类型赋值给变量，同一个变量可以反复赋值，而且可以是不同类型的变量
a = 11
print(a)
a = 'aa'
print(a)


a = 'ABC'
b = a
a = 'XYZ'
print(b) // ABC


常量
通常用全部大写的变量名表示常量
PI = 3.14159265359

```

## 2.2 字符串和编码

编码
```
计算机只能处理数字，如果要处理文本，就必须先把文本转换为数字才能处理。
不同编码对应的字节不同，一般字母是1byte，汉字是2byte
1byte = 8bit (0-255个数)

ASCII编码，用1个字节表示一个字符，原始只有127个字符  A=65 z=122
GB2312编码，中文2个字节
Unicode字符标准，把所有语言都统一到一套编码里，通常用2个字节表示一个字符

UTF-8编码，可变长编码，常用的英文字母被编码成1个字节，汉字通常是3个字节，只有很生僻的字符才会被编码成4-6个字节，UTF-8编码能节省空间

GBK(GB2312编码)汉字是2个字节

字符	ASCII	    Unicode	            UTF-8
A	    01000001	00000000 01000001	01000001
中	    x	        01001110 00101101	11100100 10111000 10101101

ASCII编码实际上可以被看成是UTF-8编码的一部分
```

在计算机内存中，统一使用Unicode编码，当需要保存到硬盘或者需要传输的时候，就转换为UTF-8编码。
用记事本编辑的时候，从文件读取的UTF-8字符被转换为Unicode字符到内存里，编辑完成后，保存的时候再把Unicode转换为UTF-8保存到文件

浏览网页的时候，服务器会把动态生成的Unicode内容转换为UTF-8再传输到浏览器
所以很多网页的源码上会有类似<meta charset="UTF-8" />的信息，表示该网页正是用的UTF-8编码。

字符串
```
在最新的Python3版本中，字符串都是以Unicode编码的。

对于单个字符的编码，Python提供了ord()函数获取字符的整数表示，chr()函数把编码转换为对应的字符：
>>> ord('A')
65
>>> ord('中')
20013
>>> chr(66)
'B'
>>> chr(25991)
'文'


如果知道字符的整数编码，还可以用十六进制这么写str：
>>> '\u4e2d\u6587'
'中文'
两种写法完全是等价的


str类型和bytes类型：
由于Python的字符串类型是str，在内存中以Unicode表示，一个字符对应若干个字节。如果要在网络上传输，或者保存到磁盘上，就需要把str变为以字节为单位的bytes。
Python对bytes类型的数据用带b前缀的单引号或双引号表示：
x = b'ABC'

要注意区分'ABC'和b'ABC'，前者是str，后者虽然内容显示得和前者一样，但bytes的每个字符都只占用一个字节。

以Unicode表示的str通过encode()方法可以编码为指定的bytes，例如：

>>> 'ABC'.encode('ascii')
b'ABC'
>>> '中文'.encode('utf-8')
b'\xe4\xb8\xad\xe6\x96\x87'
>>> '中文'.encode('ascii')
Traceback (most recent call last):
  File "<stdin>", line 1, in <module>
UnicodeEncodeError: 'ascii' codec can't encode characters in position 0-1: ordinal not in range(128)
纯英文的str可以用ASCII编码为bytes，内容是一样的，含有中文的str可以用UTF-8编码为bytes。含有中文的str无法用ASCII编码，因为中文编码的范围超过了ASCII编码的范围，Python会报错。

在bytes中，无法显示为ASCII字符的字节，用\x##显示。

反过来，如果我们从网络或磁盘上读取了字节流，那么读到的数据就是bytes。要把bytes变为str，就需要用decode()方法：

>>> b'ABC'.decode('ascii')
'ABC'
>>> b'\xe4\xb8\xad\xe6\x96\x87'.decode('utf-8')
'中文'

如果bytes中包含无法解码的字节，decode()方法会报错：
>>> b'\xe4\xb8\xad\xff'.decode('utf-8')
Traceback (most recent call last):
  ...
UnicodeDecodeError: 'utf-8' codec can't decode byte 0xff in position 3: invalid start byte

如果bytes中只有一小部分无效的字节，可以传入errors='ignore'忽略错误的字节：
>>> b'\xe4\xb8\xad\xff'.decode('utf-8', errors='ignore')
'中'


计算str包含多少个字符，可以用len()函数：
>>> len('ABC')
3
>>> len('中文')
2

len()函数计算的是str的字符数，如果换成bytes，len()函数就计算字节数：
>>> len(b'ABC')
3
>>> len(b'\xe4\xb8\xad\xe6\x96\x87')
6
>>> len('中文'.encode('utf-8'))
6
可见，1个中文字符经过UTF-8编码后通常会占用3个字节，而1个英文字符只占用1个字节。

为了避免乱码问题，应当始终坚持使用UTF-8编码对str和bytes进行转换。


#!/usr/bin/env python3
# -*- coding: utf-8 -*-

源代码也是一个文本文件，所以当源代码中包含中文的时候，在保存源代码时，就需要务必指定保存为UTF-8编码。当Python解释器读取源代码时，为了让它按UTF-8编码读取，需要指定编码格式。

解释：
第一行注释是为了告诉Linux/OS X系统，这是一个Python可执行程序，Windows系统会忽略这个注释；
第二行注释是为了告诉Python解释器，按照UTF-8编码读取源代码，否则，你在源代码中写的中文输出可能会有乱码。
申明了UTF-8编码并不意味着你的.py文件就是UTF-8编码的，必须并且要确保文本编辑器正在使用UTF-8 without BOM编码。

```

字符串格式化
```
1. %
如果只有一个%?，括号可以省略。有几个%?占位符，后面就跟几个变量或者值，顺序要对应好。
%d	整数
%f	浮点数
%s	字符串
%x	十六进制整数
>>> "Hello, %s" % 'world'
'Hello, world'
>>> 'Hi, %s, you have $%d.' % ('Michael', 1000000)
'Hi, Michael, you have $1000000.'

其中，格式化整数和浮点数还可以指定是否补0和整数与小数的位数：
print('%2d-%02d' % (3, 1))
print('%.2f' % 3.1415926)

%s永远起作用，它会把任何数据类型转换为字符串
>>> 'Age: %s. Gender: %s' % (25, True)
'Age: 25. Gender: True'

转义，用%%来表示一个%
>>> 'growth rate: %d %%' % 7
'growth rate: 7 %'

2. format()
字符串的format()方法，它会用传入的参数依次替换字符串内的占位符{0}、{1}
>>> 'Hello, {0}, 成绩提升了 {1:.1f}%'.format('小明', 17.125)
'Hello, 小明, 成绩提升了 17.1%'

3. f-string
以f开头的字符串，称之为f-string，它和普通字符串不同之处在于，字符串如果包含{xxx}，就会以对应的变量替换：
>>> r = 2.5
>>> s = 3.14 * r ** 2
>>> print(f'The area of a circle with radius {r} is {s:.2f}')
The area of a circle with radius 2.5 is 19.62

```

更多编码讨论
http://www.ruanyifeng.com/blog/2007/10/ascii_unicode_and_utf-8.html


## 2.3 list和tuple
list和tuple是Python内置的有序集合，一个可变，一个不可变。

list
有序的集合
元素的数据类型可以不同
```
>>> L = ['Apple', 123, True]

>>> classmates = ['Michael', 'Bob', 'Tracy']
>>> classmates
['Michael', 'Bob', 'Tracy']

len()函数可以获得list元素的个数
>>> len(classmates)
3

索引来访问list中每一个位置的元素
>>> classmates[0]
'Michael'
索引超出了范围时，Python会报一个IndexError错误

还可以用-1做索引，直接获取最后一个元素
>>> classmates[-1]
'Tracy'

追加元素到末尾
>>> classmates.append('Adam')
>>> classmates
['Michael', 'Bob', 'Tracy', 'Adam']

把元素插入到指定的位置
>>> classmates.insert(1, 'Jack')
>>> classmates
['Michael', 'Jack', 'Bob', 'Tracy', 'Adam']

删除list末尾的元素
>>> classmates.pop()
'Adam'

删除指定位置的元素
>>> classmates.pop(1)
'Jack'

把某个元素替换成别的元素
>>> classmates[1] = 'Sarah'
>>> classmates

```

元组 tuple
有序列表
tuple和list非常类似，但是tuple一旦初始化就不能修改，在定义的时候，tuple的元素就必须被确定下来。
没有append()，insert()这样的方法。其他获取元素的方法和list是一样的
如果可能，能用tuple代替list就尽量用tuple。
```
>>> t = (1, 2)
>>> t
(1, 2)

定义一个空的tuple
>>> t = ()
>>> t
()

定义一个只有1个元素的tuple
>>> t = (1,)
>>> t
(1,)
不能t = (1)。这是因为括号()既可以表示tuple，又可以表示数学公式中的小括号，这就产生了歧义


```
## 2.4 dict和set

字典 dict
dict的key必须是不可变对象。
dict内部存放的顺序和key放入的顺序没有关系。

```
>>> d = {'Michael': 95, 'Bob': 75, 'Tracy': 85}
>>> d['Michael']
95

放入或修改元素
>>> d['Adam'] = 67
>>> d['Adam']
67

如果key不存在，dict就会报错：
>>> d['Thomas']
Traceback (most recent call last):
  File "<stdin>", line 1, in <module>
KeyError: 'Thomas

通过in判断key是否存在：
>>> 'Thomas' in d
False

dict提供的get()方法，如果key不存在，可以返回None，或者自己指定的value：
>>> d.get('Thomas')
>>> d.get('Thomas', -1)
-1

删除一个key，用pop(key)方法，对应的value也会从dict中删除：
>>> d.pop('Bob')
75
>>> d
{'Michael': 95, 'Tracy': 85}

```

set
和dict类似，也是一组key的集合，但不存储value。
key不重复。
不是有序的。

```
要创建一个set，需要提供一个list作为输入集合：
>>> s = set([1, 2, 3])
>>> s
{1, 2, 3}

通过add(key)方法可以添加元素到set中，可以重复添加，但不会有效果：
>>> s.add(4)
>>> s

通过remove(key)方法可以删除元素：
>>> s.remove(4)
>>> s

set可以看成数学意义上的无序和无重复元素的集合，因此，两个set可以做数学意义上的交集、并集等操作：
>>> s1 = set([1, 2, 3])
>>> s2 = set([2, 3, 4])
>>> s1 & s2
{2, 3}
>>> s1 | s2
{1, 2, 3, 4}
```

set和dict的唯一区别仅在于没有存储对应的value。
set的原理和dict一样，所以同样不可以放入可变对象，因为无法判断两个可变对象是否相等，也就无法保证set内部“不会有重复元素”。

## 2.5 条件判断
```
age = 3
if age >= 18:
    print('adult')
elif age >= 6:
    print('teenager')
else:
    print('kid')

input()返回的数据类型是str，str不能直接和整数比较，必须先把str转换成整数。
s = input('birth: ')
birth = int(s)
if birth < 2000:
    print('00前')
else:
    print('00后')


```


## 2.6 循环

```
1. for x in ...循环，就是把每个元素代入变量x，然后执行缩进块的语句。
sum = 0
for x in [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]:
    sum = sum + x
print(sum)

range()函数，可以生成一个整数序列，再通过list()函数可以转换为list。
>>> list(range(5))
[0, 1, 2, 3, 4]

sum = 0
for x in range(101):
    sum = sum + x
print(sum)


2. while循环
sum = 0
n = 99
while n > 0:
    sum = sum + n
    n = n - 2
print(sum)

在循环中，break语句可以提前退出循环。
在循环过程中，也可以通过continue语句，跳过当前的这次循环，直接开始下一次循环。


```

# 3 函数

函数就是最基本的一种代码抽象的方式。

def 关键字

## 3.1 函数调用

有很多内置函数

```
>>> abs(100)
100
>>> abs(-20)
20
>>> abs(12.34)
12.34

>>> max(1, 2)
2
>>> max(2, 3, 1, -5)
3

数据类型转换函数
>>> int('123')
123
>>> int(12.34)
12
>>> float('12.34')
12.34
>>> str(1.23)
'1.23'
>>> str(100)
'100'
>>> bool(1)
True
>>> bool('')
False

函数名其实就是指向一个函数对象的引用，完全可以把函数名赋给一个变量，相当于给这个函数起了一个“别名”
>>> a = abs # 变量a指向abs函数
>>> a(-1) # 所以也可以通过a调用abs函数
1
```

## 3.2 函数定义

```
return None可以简写为return

pass可以用来作为占位符，让代码能运行起来。

数据类型检查可以用内置函数isinstance()实现
def my_abs(x):
    if not isinstance(x, (int, float)):
        raise TypeError('bad operand type')
    if x >= 0:
        return x
    else:
        return -x
        
多值返回
Python的函数返回多值其实就是返回一个tuple，但写起来更方便。
```



## 3.3 函数参数

默认参数、可变参数和关键字参数

```
1. 位置参数
def power(x, n):
    s = 1
    while n > 0:
        n = n - 1
        s = s * x
    return s

传入的两个值按照位置顺序依次赋给参数x和n。

2. 默认参数
可以扩展函数的功能。

def power(x, n=2):
    s = 1
    while n > 0:
        n = n - 1
        s = s * x
    return s

注意：
必选参数在前，默认参数在后，否则Python的解释器会报错
有多个默认参数时，调用的时候，既可以按顺序提供默认参数，也可以不按顺序提供部分默认参数。当不按顺序提供部分默认参数时，需要把参数名写上。

默认参数有个最大的坑：
def add_end(L=[]):
    L.append('END')
    return L
多次调用
>>> add_end()
['END', 'END']
>>> add_end()
['END', 'END', 'END']
Python函数在定义的时候，默认参数L的值就被计算出来了，即[]，因为默认参数L也是一个变量，它指向对象[]，每次调用该函数，如果改变了L的内容，则下次调用时，默认参数的内容就变了，不再是函数定义时的[]了。

默认参数必须指向不变对象！

修改：
def add_end(L=None):
    if L is None:
        L = []
    L.append('END')
    return L

现在，无论调用多少次，都不会有问题：
>>> add_end()
['END']
>>> add_end()
['END']

3. 可变参数
在参数前面加了一个*号。
可变参数允许你传入0个或任意个参数，这些可变参数在函数调用时自动组装为一个tuple。
可以扩展函数的功能。

def calc(numbers):
    sum = 0
    for n in numbers:
        sum = sum + n * n
    return sum
调用的时候，需要先组装出一个list或tuple：
>>> calc([1, 2, 3])
14
>>> calc((1, 3, 5, 7))
84    

利用可变参数，调用函数的方式可以简化成这样：
def calc(*numbers):
    sum = 0
    for n in numbers:
        sum = sum + n * n
    return sum
>>> calc(1, 3, 5, 7)
84
调用该函数时，可以传入任意个参数，包括0个参数

如果已经有一个list或者tuple，要调用一个可变参数怎么办？可以这样做：
>>> nums = [1, 2, 3]
>>> calc(nums[0], nums[1], nums[2])
14
或者
>>> nums = [1, 2, 3]
>>> calc(*nums)
14
Python允许你在list或tuple前面加一个*号，把list或tuple的元素变成可变参数传进去
*nums表示把nums这个list的所有元素作为可变参数传进去。

4. 关键字参数
允许传入0个或任意个含参数名的参数，这些关键字参数在函数内部自动组装为一个dict。
可以扩展函数的功能。

def person(name, age, **kw):
    print('name:', name, 'age:', age, 'other:', kw)
>>> person('Michael', 30)
name: Michael age: 30 other: {}
>>> person('Bob', 35, city='Beijing')
name: Bob age: 35 other: {'city': 'Beijing'}
>>> person('Adam', 45, gender='M', job='Engineer')
name: Adam age: 45 other: {'gender': 'M', 'job': 'Engineer'}

和可变参数类似，也可以先组装出一个dict，然后，把该dict转换为关键字参数传进去：
>>> extra = {'city': 'Beijing', 'job': 'Engineer'}
>>> person('Jack', 24, city=extra['city'], job=extra['job'])
name: Jack age: 24 other: {'city': 'Beijing', 'job': 'Engineer'}

当然，上面复杂的调用可以用简化的写法：
>>> extra = {'city': 'Beijing', 'job': 'Engineer'}
>>> person('Jack', 24, **extra)
name: Jack age: 24 other: {'city': 'Beijing', 'job': 'Engineer'}
**extra表示把extra这个dict的所有key-value用关键字参数传入到函数的**kw参数，kw将获得一个dict。
注意kw获得的dict是extra的一份拷贝，对kw的改动不会影响到函数外的extra。

5. 命名关键字参数
对于关键字参数，函数的调用者可以传入任意不受限制的关键字参数。至于到底传入了哪些，就需要在函数内部通过kw检查。

def person(name, age, **kw):
    if 'city' in kw:
        # 有city参数
        pass
    if 'job' in kw:
        # 有job参数
        pass
    print('name:', name, 'age:', age, 'other:', kw)
    
但是调用者仍可以传入不受限制的关键字参数：
>>> person('Jack', 24, city='Beijing', addr='Chaoyang', zipcode=123456)

如果要限制关键字参数的名字，就可以用命名关键字参数，例如，只接收city和job作为关键字参数。这种方式定义的函数如下：
def person(name, age, *, city, job):
    print(name, age, city, job)

和关键字参数**kw不同，命名关键字参数需要一个特殊分隔符*，*后面的参数被视为命名关键字参数。    
调用方式如下：
>>> person('Jack', 24, city='Beijing', job='Engineer')
Jack 24 Beijing Engineer

如果函数定义中已经有了一个可变参数，后面跟着的命名关键字参数就不再需要一个特殊分隔符*了：
def person(name, age, *args, city, job):
    print(name, age, args, city, job)
    
命名关键字参数必须传入参数名，这和位置参数不同。如果没有传入参数名，调用将报错：
>>> person('Jack', 24, 'Beijing', 'Engineer')
Traceback (most recent call last):
  File "<stdin>", line 1, in <module>
TypeError: person() takes 2 positional arguments but 4 were given

命名关键字参数可以有缺省值，从而简化调用：
def person(name, age, *, city='Beijing', job):
    print(name, age, city, job)
由于命名关键字参数city具有默认值，调用时，可不传入city参数：
>>> person('Jack', 24, job='Engineer')
Jack 24 Beijing Engineer

使用命名关键字参数时，要特别注意，如果没有可变参数，就必须加一个*作为特殊分隔符。如果缺少*，Python解释器将无法识别位置参数和命名关键字参数：
def person(name, age, city, job):
    # 缺少 *，city和job被视为位置参数
    pass

6. 参数组合
在Python中定义函数，可以用必选参数、默认参数、可变参数、关键字参数和命名关键字参数，这5种参数都可以组合使用。但是请注意，参数定义的顺序必须是：必选参数、默认参数、可变参数、命名关键字参数和关键字参数。

def f1(a, b, c=0, *args, **kw):
    print('a =', a, 'b =', b, 'c =', c, 'args =', args, 'kw =', kw)

def f2(a, b, c=0, *, d, **kw):
    print('a =', a, 'b =', b, 'c =', c, 'd =', d, 'kw =', kw)
    
>>> f1(1, 2)
a = 1 b = 2 c = 0 args = () kw = {}
>>> f1(1, 2, c=3)
a = 1 b = 2 c = 3 args = () kw = {}
>>> f1(1, 2, 3, 'a', 'b')
a = 1 b = 2 c = 3 args = ('a', 'b') kw = {}
>>> f1(1, 2, 3, 'a', 'b', x=99)
a = 1 b = 2 c = 3 args = ('a', 'b') kw = {'x': 99}
>>> f2(1, 2, d=99, ext=None)
a = 1 b = 2 c = 0 d = 99 kw = {'ext': None}    

最神奇的是通过一个tuple和dict，也可以调用上述函数：
>>> args = (1, 2, 3, 4)
>>> kw = {'d': 99, 'x': '#'}
>>> f1(*args, **kw)
a = 1 b = 2 c = 3 args = (4,) kw = {'d': 99, 'x': '#'}
>>> args = (1, 2, 3)
>>> kw = {'d': 88, 'x': '#'}
>>> f2(*args, **kw)
a = 1 b = 2 c = 3 d = 88 kw = {'x': '#'}

所以，对于任意函数，都可以通过类似func(*args, **kw)的形式调用它，无论它的参数是如何定义的。

注意：
虽然可以组合多达5种参数，但不要同时使用太多的组合，否则函数接口的可理解性很差。

```

小结：

```
默认参数一定要用不可变对象，如果是可变对象，程序运行时会有逻辑错误！

要注意定义可变参数和关键字参数的语法：
*args是可变参数，args接收的是一个tuple；
**kw是关键字参数，kw接收的是一个dict。

调用函数时如何传入可变参数和关键字参数的语法：
可变参数既可以直接传入：func(1, 2, 3)，又可以先组装list或tuple，再通过*args传入：func(*(1, 2, 3))；
关键字参数既可以直接传入：func(a=1, b=2)，又可以先组装dict，再通过**kw传入：func(**{'a': 1, 'b': 2})。

使用*args和**kw是Python的习惯写法，当然也可以用其他参数名，但最好使用习惯用法。

命名的关键字参数是为了限制调用者可以传入的参数名，同时可以提供默认值。

定义命名的关键字参数在没有可变参数的情况下不要忘了写分隔符*，否则定义的将是位置参数。

```

## 3.4 递归

理论上，所有的递归函数都可以写成循环的方式，但循环的逻辑不如递归清晰。

使用递归函数需要注意防止栈溢出。



# 4. 高级特性

请始终牢记，代码越少，开发效率越高。



## 4.1 切片

```
L[0:3]表示，从索引0开始取，直到索引3为止，但不包括索引3。即索引0，1，2，正好是3个元素。

如果第一个索引是0，还可以省略：
>>> L[:3]

同样支持倒数切片：
>>> L[-2:]
倒数第一个元素的索引是-1。

>>> L = list(range(100))
>>> L
[0, 1, 2, 3, ..., 99]
前10个数：
>>> L[:10]
[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
后10个数：
>>> L[-10:]
[90, 91, 92, 93, 94, 95, 96, 97, 98, 99]

只写[:]就可以原样复制一个list：
>>> L[:]
[0, 1, 2, 3, ..., 99]

tuple也可以用切片操作，只是操作的结果仍是tuple：
>>> (0, 1, 2, 3, 4, 5)[:3]
(0, 1, 2)

字符串也可以用切片操作，只是操作结果仍是字符串：
>>> 'ABCDEFG'[:3]
'ABC'
Python没有针对字符串的截取函数，只需要切片一个操作就可以完成



```



### 4.1.1 [::-1]

```
b = a[i:j:s]
i,j标识下标，s表示步数，缺省为1

a[i:j:1]相当于a[i:j]

当s<0时，i缺省时，默认为-1. 
j缺省时，默认为-len(a)-1
所以a[::-1]相当于 a[-1:-len(a)-1:-1]，也就是从最后一个元素到第一个元素复制一遍。

s = [1, 2, 3]
s = s[::-1]
```





## 4.2 迭代

```
在Python中，迭代是通过for ... in来完成的

Python的for循环不仅可以用在list或tuple上，还可以作用在其他可迭代对象上。

只要是可迭代对象，无论有无下标，都可以迭代
>>> d = {'a': 1, 'b': 2, 'c': 3}
>>> for key in d:
...     print(key)

默认情况下，dict迭代的是key。如果要迭代value，可以用for value in d.values()，如果要同时迭代key和value，可以用for k, v in d.items()。

字符串也是可迭代对象:
>>> for ch in 'ABC':
...     print(ch)

判断一个对象是可迭代对象呢？方法是通过collections.abc模块的Iterable类型判断：
>>> from collections.abc import Iterable
>>> isinstance('abc', Iterable) # str是否可迭代
True
>>> isinstance([1,2,3], Iterable) # list是否可迭代
True
>>> isinstance(123, Iterable) # 整数是否可迭代
False

Python内置的enumerate函数可以把一个list变成索引-元素对，这样就可以在for循环中同时迭代索引和元素本身：
>>> for i, value in enumerate(['A', 'B', 'C']):
...     print(i, value)
...
0 A
1 B
2 C

上面的for循环里，同时引用了两个变量，在Python里是很常见的，比如下面的代码：
>>> for x, y in [(1, 1), (2, 4), (3, 9)]:
...     print(x, y)
...
1 1
2 4
3 9


```



## 4.3 列表生成式

列表生成式即List Comprehensions，是Python内置的非常简单却强大的可以用来创建list的生成式。

运用列表生成式，可以快速生成list，可以通过一个list推导出另一个list，而代码却十分简洁。



```
>>> list(range(1, 11))
[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]

>>> L = []
>>> for x in range(1, 11):
...    L.append(x * x)
>>> [x * x for x in range(1, 11)]
[1, 4, 9, 16, 25, 36, 49, 64, 81, 100]

>>> [x * x for x in range(1, 11) if x % 2 == 0]
[4, 16, 36, 64, 100]

生成全排列：
>>> [m + n for m in 'ABC' for n in 'XYZ']
['AX', 'AY', 'AZ', 'BX', 'BY', 'BZ', 'CX', 'CY', 'CZ']

列出当前目录下的所有文件和目录名，可以通过一行代码实现：
>>> import os
>>> [d for d in os.listdir('.')]

for循环其实可以同时使用两个甚至多个变量，比如dict的items()可以同时迭代key和value：
>>> d = {'x': 'A', 'y': 'B', 'z': 'C' }
>>> for k, v in d.items():
...     print(k, '=', v)

可以简写为：
>>> d = {'x': 'A', 'y': 'B', 'z': 'C' }
>>> [k + '=' + v for k, v in d.items()]
['y=B', 'x=A', 'z=C']

把一个list中所有的字符串变成小写：
>>> L = ['Hello', 'World', 'IBM', 'Apple']
>>> [s.lower() for s in L]
['hello', 'world', 'ibm', 'apple']

总结：
在一个列表生成式中，for前面的if ... else是表达式，而for后面的if是过滤条件，不能带else。

# -*- coding: utf-8 -*-
L1 = ['Hello', 'World', 18, 'Apple', None]
L2 = [x.lower() if isinstance(x, str) else x for x in L1]
L3 = [x.lower() for x in L1 if isinstance(x, str)]
```



## 4.4 生成器

```
在Python中，这种一边循环一边计算的机制，称为生成器：generator。

1. 第一种方法很简单，只要把一个列表生成式的[]改成()，就创建了一个generator：
>>> L = [x * x for x in range(10)]
>>> L
[0, 1, 4, 9, 16, 25, 36, 49, 64, 81]
>>> g = (x * x for x in range(10))
>>> g
<generator object <genexpr> at 0x1022ef630>

可以通过next()函数获得generator的下一个返回值：
>>> next(g)
0
>>> next(g)
1
>>> next(g)
4
>>> next(g)
Traceback (most recent call last):
  File "<stdin>", line 1, in <module>
StopIteration

generator保存的是算法，每次调用next(g)，就计算出g的下一个元素的值，直到计算到最后一个元素，没有更多的元素时，抛出StopIteration的错误。

也可以for循环调用
>>> g = (x * x for x in range(10))
>>> for n in g:
...     print(n)
通过for循环来迭代它，并且不需要关心StopIteration的错误。

2. generator非常强大。如果推算的算法比较复杂，用类似列表生成式的for循环无法实现的时候，还可以用函数来实现。
def fib(max):
    n, a, b = 0, 0, 1
    while n < max:
        print(b)
        a, b = b, a + b
        n = n + 1
    return 'done'
注意，赋值语句：
a, b = b, a + b
相当于：
t = (b, a + b) # t是一个tuple
a = t[0]
b = t[1]
但不必显式写出临时变量t就可以赋值。    

用生成器实现
def fib(max):
    n, a, b = 0, 0, 1
    while n < max:
        yield b
        a, b = b, a + b
        n = n + 1
    return 'done'
如果一个函数定义中包含yield关键字，那么这个函数就不再是一个普通函数，而是一个generator：
>>> f = fib(6)
>>> f
<generator object fib at 0x104feaaa0>    

generator和函数的执行流程不一样。函数是顺序执行，遇到return语句或者最后一行函数语句就返回。而变成generator的函数，在每次调用next()的时候执行，遇到yield语句返回，再次执行时从上次返回的yield语句处继续执行。

def odd():
    print('step 1')
    yield 1
    print('step 2')
    yield(3)
    print('step 3')
    yield(5)
>>> o = odd()
>>> next(o)
step 1
1
>>> next(o)
step 2
3
>>> next(o)
step 3
5
>>> next(o)
Traceback (most recent call last):
  File "<stdin>", line 1, in <module>
StopIteration    
可以看到，odd不是普通函数，而是generator，在执行过程中，遇到yield就中断，下次又继续执行。执行3次yield后，已经没有yield可以执行了，所以，第4次调用next(o)就报错。

把函数改成generator后，我们基本上从来不会用next()来获取下一个返回值，而是直接使用for循环来迭代

用for循环调用generator时，发现拿不到generator的return语句的返回值。如果想要拿到返回值，必须捕获StopIteration错误，返回值包含在StopIteration的value中：
>>> g = fib(6)
>>> while True:
...     try:
...         x = next(g)
...         print('g:', x)
...     except StopIteration as e:
...         print('Generator return value:', e.value)
...         break


用生成器输出杨辉三角
         1
         / \
        1   1
       / \ / \
      1   2   1
     / \ / \ / \
    1   3   3   1
   / \ / \ / \ / \
  1   4   6   4   1
 / \ / \ / \ / \ / \
1   5   10  10  5   1

def triangles():
	L = [1]
    while True:
        yield L[:]
        L.append(0)
        L = [L[i]+L[i-1] for i in range(len(L))]


小结:
generator是非常强大的工具，在Python中，可以简单地把列表生成式改成generator，也可以通过函数实现复杂逻辑的generator。

要理解generator的工作原理，它是在for循环的过程中不断计算出下一个元素，并在适当的条件结束for循环。对于函数改成的generator来说，遇到return语句或者执行到函数体最后一行语句，就是结束generator的指令，for循环随之结束。

请注意区分普通函数和generator函数，普通函数调用直接返回结果：
>>> r = abs(6)
>>> r
6
generator函数的“调用”实际返回一个generator对象：
>>> g = fib(6)
>>> g
<generator object fib at 0x1022ef948>
```



## 4.5 迭代器

```
可以直接作用于for循环的数据类型有以下几种：
一类是集合数据类型，如list、tuple、dict、set、str等；
一类是generator，包括生成器和带yield的generator function。
这些可以直接作用于for循环的对象统称为可迭代对象：Iterable。

可以使用isinstance()判断一个对象是否是Iterable对象：
>>> from collections.abc import Iterable
>>> isinstance([], Iterable)
True
>>> isinstance({}, Iterable)
True
>>> isinstance('abc', Iterable)
True
>>> isinstance((x for x in range(10)), Iterable)
True
>>> isinstance(100, Iterable)
False

而生成器不但可以作用于for循环，还可以被next()函数不断调用并返回下一个值，直到最后抛出StopIteration错误表示无法继续返回下一个值了。
可以被next()函数调用并不断返回下一个值的对象称为迭代器：Iterator。

可以使用isinstance()判断一个对象是否是Iterator对象：
>>> from collections.abc import Iterator
>>> isinstance((x for x in range(10)), Iterator)
True
>>> isinstance([], Iterator)
False
>>> isinstance({}, Iterator)
False
>>> isinstance('abc', Iterator)
False
生成器都是Iterator对象，但list、dict、str虽然是Iterable，却不是Iterator。

把list、dict、str等Iterable变成Iterator可以使用iter()函数：
>>> isinstance(iter([]), Iterator)
True
>>> isinstance(iter('abc'), Iterator)
True

为什么list、dict、str等数据类型不是Iterator？
这是因为Python的Iterator对象表示的是一个数据流，Iterator对象可以被next()函数调用并不断返回下一个数据，直到没有数据时抛出StopIteration错误。可以把这个数据流看做是一个有序序列，但我们却不能提前知道序列的长度，只能不断通过next()函数实现按需计算下一个数据，所以Iterator的计算是惰性的，只有在需要返回下一个数据时它才会计算。

Iterator甚至可以表示一个无限大的数据流，例如全体自然数。而使用list是永远不可能存储全体自然数的。

小结：
凡是可作用于for循环的对象都是Iterable类型；
凡是可作用于next()函数的对象都是Iterator类型，它们表示一个惰性计算的序列；
集合数据类型如list、dict、str等是Iterable但不是Iterator，不过可以通过iter()函数获得一个Iterator对象。

Python的for循环本质上就是通过不断调用next()函数实现的，例如：
for x in [1, 2, 3, 4, 5]:
    pass
实际上完全等价于：
# 首先获得Iterator对象:
it = iter([1, 2, 3, 4, 5])
# 循环:
while True:
    try:
        # 获得下一个值:
        x = next(it)
    except StopIteration:
        # 遇到StopIteration就退出循环
        break
```

# 5. 函数式编程

函数就是面向过程的程序设计的基本单元。

Functional Programming

函数式编程的一个特点就是，允许把函数本身作为参数传入另一个函数，还允许返回一个函数！



Python中，函数本身也可以赋值给变量，即：变量可以指向函数。

## 5.1 高阶函数

变量可以指向函数

函数名也是变量，函数名其实就是指向函数的变量

```
>>> abs = 10
>>> abs(-10)
Traceback (most recent call last):
  File "<stdin>", line 1, in <module>
TypeError: 'int' object is not callable
`abs`函数实际上是定义在`import builtins`模块中的，所以要让修改abs变量的指向在其它模块也生效，要用import builtins; builtins.abs = 10。
```

**一个函数就可以接收另一个函数作为参数，这种函数就称之为高阶函数。**

```
def add(x, y, f):
    return f(x) + f(y)

>>>print(add(-5, 6, abs))     
```

### 5.1.1 map/reduce

Python内建了`map()`和`reduce()`函数

http://research.google.com/archive/mapreduce.html

`map()`函数接收两个参数，一个是函数，一个是`Iterable`，`map`将传入的函数依次作用到序列的每个元素，并把结果作为新的`Iterator`返回。

```
>>> def f(x):
...     return x * x
...
>>> r = map(f, [1, 2, 3, 4, 5, 6, 7, 8, 9])
>>> list(r)
[1, 4, 9, 16, 25, 36, 49, 64, 81]

把这个list所有数字转为字符串：
>>> list(map(str, [1, 2, 3, 4, 5, 6, 7, 8, 9]))
['1', '2', '3', '4', '5', '6', '7', '8', '9']

```

`reduce`把一个函数作用在一个序列`[x1, x2, x3, ...]`上，这个函数必须接收两个参数，`reduce`把结果继续和序列的下一个元素做累积计算，其效果就是：

```
reduce(f, [x1, x2, x3, x4]) = f(f(f(x1, x2), x3), x4)

对一个序列求和，就可以用reduce实现：
>>> from functools import reduce
>>> def add(x, y):
...     return x + y
...
>>> reduce(add, [1, 3, 5, 7, 9])
25
当然求和运算可以直接用Python内建函数sum()，没必要动用reduce。

如果要把序列[1, 3, 5, 7, 9]变换成整数13579，reduce就可以派上用场：
>>> from functools import reduce
>>> def fn(x, y):
...     return x * 10 + y
...
>>> reduce(fn, [1, 3, 5, 7, 9])
13579

考虑到字符串str也是一个序列，配合map()，可以写出把str转换为int的函数：
>>> from functools import reduce
>>> def fn(x, y):
...     return x * 10 + y
...
>>> def char2num(s):
...     digits = {'0': 0, '1': 1, '2': 2, '3': 3, '4': 4, '5': 5, '6': 6, '7': 7, '8': 8, '9': 9}
...     return digits[s]
...
>>> reduce(fn, map(char2num, '13579'))
13579

整理成一个str2int的函数就是：
from functools import reduce
DIGITS = {'0': 0, '1': 1, '2': 2, '3': 3, '4': 4, '5': 5, '6': 6, '7': 7, '8': 8, '9': 9}
def str2int(s):
    def fn(x, y):
        return x * 10 + y
    def char2num(s):
        return DIGITS[s]
    return reduce(fn, map(char2num, s))
    
还可以用lambda函数进一步简化成：
from functools import reduce
DIGITS = {'0': 0, '1': 1, '2': 2, '3': 3, '4': 4, '5': 5, '6': 6, '7': 7, '8': 8, '9': 9}
def char2num(s):
    return DIGITS[s]
def str2int(s):
    return reduce(lambda x, y: x * 10 + y, map(char2num, s))
    
在Python数学运算中*代表乘法，**为指数运算

示例：
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

    print(mul([3, 5, 7, 9]))
    print(mul1([3, 5, 7, 9]))
    print(list(c1(['adam', 'LISA', 'barT'])))
    print(c2('123.456'))
```



### 5.1.2 filter

内建的`filter()`函数用于过滤序列。

和`map()`类似，`filter()`也接收一个函数和一个序列。和`map()`不同的是，`filter()`把传入的函数依次作用于每个元素，然后根据返回值是`True`还是`False`决定保留还是丢弃该元素。

`filter()`的作用是从一个序列中筛出符合条件的元素。由于`filter()`使用了惰性计算，所以只有在取`filter()`结果的时候，才会真正筛选并每次返回下一个筛出的元素。

```
def is_odd(n):
    return n % 2 == 1
list(filter(is_odd, [1, 2, 4, 5, 6, 9, 10, 15]))
# 结果: [1, 5, 9, 15]

def not_empty(s):
    return s and s.strip()
list(filter(not_empty, ['A', '', 'B', None, 'C', '  ']))
# 结果: ['A', 'B', 'C']

注意到filter()函数返回的是一个Iterator，也就是一个惰性序列，所以要强迫filter()完成计算结果，需要用list()函数获得所有结果并返回list。


用filter求素数
计算素数的一个方法是埃氏筛法，它的算法理解起来非常简单：
首先，列出从2开始的所有自然数，构造一个序列：
2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...
取序列的第一个数2，它一定是素数，然后用2把序列的2的倍数筛掉：
3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...
取新序列的第一个数3，它一定是素数，然后用3把序列的3的倍数筛掉：
5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...
取新序列的第一个数5，然后用5把序列的5的倍数筛掉：
7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...
不断筛下去，就可以得到所有的素数。
用Python来实现这个算法，可以先构造一个从3开始的奇数序列：

	
```



### 5.1.13 sorted

内置的`sorted()`函数可以对list进行排序

`sorted()`函数是一个高阶函数，可以接收一个`key`函数来实现自定义的排序

```
>>> sorted([36, 5, -12, 9, -21])
[-21, -12, 5, 9, 36]

按绝对值大小排序：
>>> sorted([36, 5, -12, 9, -21], key=abs)
[5, 9, -12, -21, 36]
key指定的函数将作用于list的每一个元素上，并根据key函数返回的结果进行排序。

>>> sorted(['bob', 'about', 'Zoo', 'Credit'])
['Credit', 'Zoo', 'about', 'bob']
默认情况下，对字符串排序，是按照ASCII的大小比较的，由于'Z' < 'a'，结果，大写字母Z会排在小写字母a的前面。

忽略大小写，按照字母序排序
>>> sorted(['bob', 'about', 'Zoo', 'Credit'], key=str.lower)
['about', 'bob', 'Credit', 'Zoo']

进行反向排序，不必改动key函数，可以传入第三个参数reverse=True：
>>> sorted(['bob', 'about', 'Zoo', 'Credit'], key=str.lower, reverse=True)
['Zoo', 'Credit', 'bob', 'about']


# -*- coding: utf-8 -*-
L = [('Bob', 75), ('Adam', 92), ('Bart', 66), ('Lisa', 88)]
def by_name(t):
    return t[0].lower()
L2 = sorted(L, key=by_name)
print(L2)

def by_score(t):
    return -t[1]
L2 = sorted(L, key=by_score)
print(L2)
```



## 5.2 返回函数

高阶函数除了可以接受函数作为参数外，还可以把函数作为结果值返回。



```
def calc_sum(*args):
    ax = 0
    for n in args:
        ax = ax + n
    return ax
    
def lazy_sum(*args):
    def sum():
        ax = 0
        for n in args:
            ax = ax + n
        return ax
    return sum
    
>>> f = lazy_sum(1, 3, 5, 7, 9)
>>> f
<function lazy_sum.<locals>.sum at 0x101c6ed90>    
>>> f()
25
相关参数和变量都保存在返回的函数中，这种称为 闭包（Closure）

>>> f1 = lazy_sum(1, 3, 5, 7, 9)
>>> f2 = lazy_sum(1, 3, 5, 7, 9)
>>> f1==f2
False

def count():
    fs = []
    for i in range(1, 4):
        def f():
             return i*i
        fs.append(f)
    return fs
f1, f2, f3 = count()
>>> f1()
9
>>> f2()
9
>>> f3()
9
返回的函数引用了变量i，但它并非立刻执行。等到3个函数都返回时，它们所引用的变量i已经变成了3，因此最终结果为9。

返回闭包时牢记一点：返回函数不要引用任何循环变量，或者后续会发生变化的变量。
返回一个函数时，牢记该函数并未执行，返回函数中不要引用任何可能会变化的变量。

如果一定要引用循环变量怎么办？方法是再创建一个函数，用该函数的参数绑定循环变量当前的值，无论该循环变量后续如何更改，已绑定到函数参数的值不变：
def count():
    def f(j):
        def g():
            return j*j
        return g
    fs = []
    for i in range(1, 4):
        fs.append(f(i)) # f(i)立刻被执行，因此i的当前值被传入f()
    return fs
再看看结果：
>>> f1, f2, f3 = count()
>>> f1()
1
>>> f2()
4
>>> f3()
9

利用闭包返回一个计数器函数，每次调用它返回递增整数：
def createCounter():
    res = [0]
    def counter():
        res[0] = res[0] + 1
        return res[0]
    return counter
```



## 5.3 匿名函数

在Python中，对匿名函数提供了有限支持。只有一些简单的情况下可以使用匿名函数。

```
>>> list(map(lambda x: x * x, [1, 2, 3, 4, 5, 6, 7, 8, 9]))
[1, 4, 9, 16, 25, 36, 49, 64, 81]

匿名函数lambda x: x * x实际上就是：
def f(x):
    return x * x
    
关键字lambda表示匿名函数，冒号前面的x表示函数参数。
匿名函数有个限制，就是只能有一个表达式，不用写return，返回值就是该表达式的结果。    

用匿名函数有个好处，因为函数没有名字，不必担心函数名冲突。
此外，匿名函数也是一个函数对象，也可以把匿名函数赋值给一个变量，再利用变量来调用该函数：
>>> f = lambda x: x * x
>>> f
<function <lambda> at 0x101c6ef28>
>>> f(5)
25

同样，也可以把匿名函数作为返回值返回，比如：
def build(x, y):
    return lambda: x * x + y * y
    
def is_odd(n):
    return n % 2 == 1
L = list(filter(is_odd, range(1, 20)))
L = list(filter(lambda x:x%2 ==1, range(1, 20)))
```



## 5.4 装饰器

在代码运行期间动态增加功能的方式，称之为“装饰器”（Decorator）。

本质上，decorator就是一个返回函数的高阶函数。



在面向对象（OOP）的设计模式中，decorator被称为装饰模式。OOP的装饰模式需要通过继承和组合来实现，而Python除了能支持OOP的decorator外，直接从语法层次支持decorator。Python的decorator可以用函数实现，也可以用类实现。

decorator可以增强函数的功能，定义起来虽然有点复杂，但使用起来非常灵活和方便。



```
>>> def now():
...     print('2015-3-25')
...
>>> f = now
>>> f()
2015-3-25

def log(func):
    def wrapper(*args, **kw):
        print('call %s():' % func.__name__)
        return func(*args, **kw)
    return wrapper

借助Python的@语法，把decorator置于函数的定义处：
@log
def now():
    print('2015-3-25')

把@log放到now()函数的定义处，相当于执行了语句：
now = log(now)

如果decorator本身需要传入参数，那就需要编写一个返回decorator的高阶函数，写出来会更复杂。比如，要自定义log的文本：
def log(text):
    def decorator(func):
        def wrapper(*args, **kw):
            print('%s %s():' % (text, func.__name__))
            return func(*args, **kw)
        return wrapper
    return decorator

@log('execute')
def now():
    print('2015-3-25')
    
相当于
>>> now = log('execute')(now)

Python内置的functools.wraps
import functools
def log(func):
    @functools.wraps(func)
    def wrapper(*args, **kw):
        print('call %s():' % func.__name__)
        return func(*args, **kw)
    return wrapper
    
或者针对带参数的decorator：
import functools
def log(text):
    def decorator(func):
        @functools.wraps(func)
        def wrapper(*args, **kw):
            print('%s %s():' % (text, func.__name__))
            return func(*args, **kw)
        return wrapper
    return decorator

实现方法执行耗时打印
def metric(fn):
    @functools.wraps(fn)
    def wrapper(*args, **kw):
        start = time.time()
        res = fn(*args, **kw)
        print('%s executed in %s ms' % (fn.__name__, time.time()-start))
        return res
    return wrapper

# 测试
@metric
def fast(x, y):
    time.sleep(0.0012)
    return x + y;    

from functools import wraps
def Round(n):
    return lambda func: wraps(func)(lambda *args, **kw: round(func(*args, **kw), n))
```



```
# -*- coding: utf-8 -*-
import functools

def log(para):
    if isinstance(para, str):
        def decorator(fn):
            @functools.wraps(fn)
            def wrapper(*args, **kw):
                print('%s call ' % para, wrapper.__name__)
                return fn(*args, **kw)
            return wrapper
        return decorator
    else:
        @functools.wraps(para)
        def wrapper(*args, **kw):
            print('call ', wrapper.__name__)
            return para(*args, **kw)
        return wrapper

@log('just')
def abc():
    print('abc')
    return 0

@log
def cba():
    print('cba')
    return 0

abc()
cba()
```

## 5. 5 偏函数

通过设定参数的默认值，可以降低函数调用的难度。而偏函数也可以做到这一点。

**创建偏函数时，实际上可以接收函数对象、`*args`和`**kw`这3个参数**

当函数的参数个数太多，需要简化时，使用`functools.partial`可以创建一个新的函数，这个新函数可以固定住原函数的部分参数，从而在调用时更简单。



```
int()函数可以把字符串转换为整数，当仅传入字符串时，int()函数默认按十进制转换：
>>> int('12345')
12345

int()函数还提供额外的base参数，默认值为10。如果传入base参数，就可以做N进制的转换：
>>> int('12345', base=8)
5349
>>> int('12345', 16)
74565

可以定义一个int2()的函数，默认把base=2传进去：
def int2(x, base=2):
    return int(x, base)
    
>>> import functools
>>> int2 = functools.partial(int, base=2)
>>> int2('1000000')
64
>>> int2('1010101')
85

functools.partial的作用就是，把一个函数的某些参数给固定住（也就是设置默认值），返回一个新的函数，调用这个新函数会更简单。

>>> int2('1000000', base=10)
1000000

int2 = functools.partial(int, base=2)
实际上固定了int()函数的关键字参数base，也就是：
int2('10010')
相当于：
kw = { 'base': 2 }
int('10010', **kw)

max2 = functools.partial(max, 10)
实际上会把10作为*args的一部分自动加到左边，也就是：
max2(5, 6, 7)
相当于：
args = (10, 5, 6, 7)
max(*args)
结果为10。


```















