# 1 简介
```
CVS、SVN 
集中式的版本控制系统
一般需要联网，每个机器需要拷贝一份完整的文件。

Git 分布式版本控制系统
每台机器有“快照”，不用整盘拷贝

分布式版本控制系统通常也有一台充当“中央服务器”的电脑，只是方便“交换修改”。
```

# 2 安装
```
Debian或Ubuntu Linux
$sudo apt-get install git
apt install -y git

其他Linux版本，通过源码安装
tar xf git.tar.gz
cd git
./config
make && sudo make install

windows
git.msi

```

## 2.1 git配置

```
git config --global user.name "your name"
git config --global user.email "your email"

--global参数，表示这台机器上所有的Git仓库都会使用这个配置
当然也可以对某个仓库指定不同的用户名和Email地址。

让Git显示颜色，会让命令输出看起来更醒目：
$git config --global color.ui true

.gitignore文件
所有配置文件可以直接在线浏览：
https://github.com/github/gitignore

可以用-f强制添加到Git
$git add -f App.class

可能是.gitignore写得有问题，需要找出来到底哪个规则写错了，可以用git check-ignore命令检查
$git check-ignore -v App.class

# 排除所有.开头的隐藏文件:
.*
# 排除所有.class文件:
*.class

# 不排除.gitignore和App.class:
!.gitignore
!App.class

继续忽略Python编译产生的.pyc、.pyo、dist等文件或目录：
# Python:
*.py[cod]
*.so
*.egg
*.egg-info
dist
build

配置别名
$git config --global alias.st status
$git st
$git status

$ git config --global alias.co checkout
$ git config --global alias.ci commit
$ git config --global alias.br branch

--global参数是全局参数，也就是这些命令在这台电脑的所有Git仓库下都有用。

$ git config --global alias.unstage 'reset HEAD'
$ git unstage test.py
$ git reset HEAD test.py

$ git config --global alias.last 'log -1'
$ git last

配置文件
每个仓库的Git配置文件都放在.git/config文件中
$ cat .git/config

当前用户的Git配置文件放在用户主目录下的一个隐藏文件.gitconfig中
$ cat ~/.gitconfig

搭建Git服务器
https://www.liaoxuefeng.com/wiki/896043488029600/899998870925664

Git也继承了开源社区的精神，不支持权限控制。不过，因为Git支持钩子（hook），所以，可以在服务器端编写一系列脚本来控制提交等操作，达到权限控制的目的。
```


# 3 常用操作

## 3.1 创建版本库
```
$cd /opt
$mkdir git1
$cd git1
$git init
$ls -ah
.git目录
```

所有的版本控制系统，其实只能跟踪文本文件的改动，二进制文件无法跟踪内容的变化。
编辑文件建议用Notepad++ UTF-8 without BOM

添加文件
```
$git add f.txt
$git add f1.txt f2.txt
$git commit -m "add f1 f2"
```

查看状态
```
$git status

查看文件差异
$git diff f.txt
```

“保存一个快照”，这个快照在Git中被称为commit
SHA1计算出来的一个非常大的数字

查看提交历史commit
```
$git log
$git log -2
$git log --state
$git log --pretty=oneline
```

在Git中，用HEAD表示当前版本，也就是最新的提交commit
上一个版本就是HEAD^，上上一个版本就是HEAD^，往上100个版本写HEAD~100

## 3.2 版本回退
```
把当前版本回退到上一个版本
$git reset --hard HEAD^
$git log
最近一次commit就没有了

回退到某一个具体的commmit
$git reset --hard commitId

找历史上所有的命令
$git reflog
可以找回退之后git log找不到的commit记录
```

## 3.3 工作区和暂存区
![aa](./image/0.jpg)

**git add 就是把untracked file提交到暂存区（Stage）**
**git commit 就是把暂存区的所有修改提交到分支。**

查看工作区和版本库里面最新版本的区别
git diff HEAD -- f.txt

每次修改，如果不用git add到暂存区，那就不会加入到commit中

## 3.4 撤销修改

1. 丢弃工作区的修改
```
$git checkout -- file 
这里有两种情况：
一种是修改后还没有被放到暂存区，现在撤销修改就回到和版本库一模一样的状态；
一种是已经添加到暂存区后，又作了修改，现在，撤销修改就回到添加到暂存区后的状态。

总之，就是让这个文件回到最近一次git commit或git add时的状态。

git checkout -- file命令中的--很重要，没有--，就变成了“切换到另一个分支”的命令
```

2. 丢弃暂存区的修改
```
把暂存区的修改撤销掉（unstage），重新放回工作区
$git reset HEAD <file>

然后再用git checkout -- file丢弃工作区的修改

$git status
整个世界终于清静了！
```

如果已经commit了，那只能版本回退了git rest --hard HEAD^

如果已经push到远程仓了，gg...

## 3.5 文件删除

```
$rm -rf f.txt
$git status

1. 真正从版本库中删除
$git rm f.txt
$git commit -m "remove f.txt"

先手动删除文件，然后使用git rm <file>和git add<file>效果是一样的。

2. 删错了，从版本库中恢复
$git checkout -- f.txt

git checkout其实是用版本库里的版本替换工作区的版本，无论工作区是修改还是删除，都可以“一键还原”。
```

从来没有被添加到版本库就被删除的文件，是无法恢复的！

# 4. 远程仓库

找一台电脑充当服务器的角色，搭建git服务。
github
gitee
gitea

git支持ssh协议、Http、Https
ssh协议速度最快。但是在某些只开放http端口的公司内部就无法使用ssh协议而只能用https
```
生成ssh Key
$ssh-keygen -t rsa -C "your email"

在用户目录下生成
$cd ~
$ls -ah
id_rsa
id_rsa.pub

windows
ssh-keygen.exe

把一个已有的本地仓库与远程库关联
$git remote add origin git@github.com:xy2/aa.git

远程库的名字就是origin，这是Git默认的叫法，也可以改成别的

推送到远程库上
$git push origin postgres

$git push -u origin branch1
把当前分支branch1推送到远程

-u参数，会把本地的master分支和远程的master分支关联起来，在以后的推送或者拉取时就可以简化命令。

查看远程库信息
$git remote
origin

$git remote -v
origin  git@github.com:michaelliao/learngit.git (fetch)
origin  git@github.com:michaelliao/learngit.git (push)

删除远程库
$git remote rm <name>
$git remote rm origin

“删除”其实是解除了本地和远程的绑定关系，并不是物理上删除了远程库。

注意：
本地库可以关联多个远程库(fork模式下工作)




远程库克隆
$git clone -b master giturl
```

# 5 分支

HEAD严格来说不是指向提交，而是指向master，master才是指向提交的，所以，HEAD指向的就是当前分支。

```
创建dev分支，然后切换到dev分支：
$git checkout -b dev

git checkout命令加上-b参数表示创建并切换，相当于以下两条命令：
$ git branch dev
$ git checkout dev

创建分支
git branch <name>

命令查看当前分支：
$git branch

合并分支
$git checkout master
$git merge dev

删除dev分支
$git branch -d dev

强行删除，需要使用大写的-D参数。
$ git branch -D dev

最新版本的Git提供了新的git switch命令来切换分支

创建并切换到新的dev分支
$git switch -c dev

切换到已有的master分支
$git switch master

```

## 5.1 冲突解决

```
Git用<<<<<<<，=======，>>>>>>>标记出不同分支的内容

git merge dev
vi t.txt
git add t.txt
git commit -m "resolve conflict"

当Git无法自动合并分支时，就必须首先解决冲突。解决冲突后，再提交，合并完成。

用git log --graph命令可以看到分支合并图。
$git log --graph --pretty=oneline --abbrev-commit
```

## 5.2 分支管理策略

通常，合并分支时，如果可能，Git会用Fast forward模式，但这种模式下，删除分支后，会丢掉分支信息。

如果要强制禁用Fast forward模式，Git就会在merge时生成一个新的commit，这样，从分支历史上就可以看出分支信息。

--no-ff参数，表示禁用Fast forward

```
$git merge --no-ff -m "merge with no-ff" dev
创建了一个新的commit


```
master 主线分支，一般不在上面干活
dev 开发分支，个人分支往上面合

![aa](image/1.png)

合并分支时，加上--no-ff参数就可以用普通模式合并，合并后的历史有分支，能看出来曾经做过合并，而fast forward合并就看不出来曾经做过合并。

## 5.3 Bug分支

```
Git提供了一个stash功能，可以把当前工作现场“储藏”起来，等以后恢复现场后继续工作
$git stash
$git status
当前工作区是干净的（除非有没有被Git管理的文件）

$git checkout dev
$git checkout -b issue-01
$vi t.txt
$git add t.txt
$git commit -m "fixed bug"

$git checkout master
$git merge --no-ff -m "merged bug fix" issue-01

恢复工作区现场
$git stash list
有两个办法：
1. 用git stash apply恢复，但是恢复后，stash内容并不删除，需要用git stash drop来删除；
2. 用git stash pop，恢复的同时把stash内容也删了
$git stash pop

恢复指定的stash
$git stash apply stash@{0}
$git stash pop stash@{0}


cherry-pick命令，能复制一个特定的提交到当前分支：
$git branch
* dev
  master
$git cherry-pick 4c805e2
Git自动给dev分支做了一次提交
```

## 5.4 多人协作

```
推送分支
$git push origin master

完整流程：
$git clone giturl
从远程库clone时，默认情况下，只能看到本地的master分支：
$git branch
* master
创建远程origin的dev分支到本地
$git checkout -b dev origin/dev
$git add t.txt
$git commit -m "add t.txt"
$git push origin dev

另外一个人也做了修改
$git add t.txt
$git commit -m "add t.txt"
$git push origin dev
提交有冲突
$git pull
如果提示：There is no tracking information for the current branch，原因是没有指定本地dev分支与远程origin/dev分支的链接
$git branch --set-upstream-to=origin/dev dev
再pull
$git pull
提示合并有冲突，需要手动解决
解决后，提交
$git commit -m "fix env conflict"
$git push origin dev
```

## 5.5 rebase

使用情形和原则： 
只对尚未推送或分享给别人的本地修改执行变基操作清理历史，从不对已推送至别处的提交执行变基操作。 
因为rebase会改变提交历史记录，这会影响到别人使用这一远程仓库。

```
git pull --rebase 
该命令会把你的提交“放置”在远程拉取的提交之后，即改变基础（变基），如果有冲突解决所有冲突的文件，git add <冲突文件>
git rebase --continue

git pull相当于是git fetch + git merge，
git pull -r，也就是git pull --rebase，相当于git fetch + git rebase

注意：
确实如果先解决冲突再rebase的话就会恢复冲突

```

推荐：
http://gitbook.liuhui998.com/4_2.html

git可视化工具
gitkraken

图形界面工具
SourceTree


# 6 标签

标签就是版本库的一个快照。就是指向某个commit的指针（但是分支可以移动，标签不能移动）
tag就是一个让人容易记住的有意义的名字，它跟某个commit绑在一起。

标签总是和某个commit挂钩。
如果这个commit既出现在master分支，又出现在dev分支，那么在这两个分支上都可以看到这个标签。

```
创建标签
git tag <name>
默认为HEAD，也可以指定一个commit id；
在指定commit点上打标签
$git tag v0.9 f52c633

$git tag v1.0

查看所有标签：
$git tag
标签不是按时间顺序列出，而是按字母排序的


创建带有说明的标签，用-a指定标签名，-m指定说明文字：
$git tag -a v0.1 -m "version 0.1 released" 1094adb

查看标签信息：
git show <tagname>

本地删除标签：
$git tag -d v0.1

推送某个标签到远程:
git push origin <tagname>

一次性推送全部尚未推送到远程的本地标签：
$git push origin --tags

删除远程标签：
先从本地删除
$git tag -d v0.9
然后，从远程删除。删除命令也是push，但是格式如下：
$git push origin :refs/tags/v0.9

```

常用操作总结：
https://liaoxuefeng.gitee.io/resource.liaoxuefeng.com/git/git-cheat-sheet.pdf
http://gitbook.liuhui998.com/4_2.html

Git官网
http://git-scm.com/