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