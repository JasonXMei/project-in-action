# git免密配置

git config --global credential.helper store

# git常用命令

* 基本操作
  同步数据：git pull
  查看状态：git status
  添加记录：git add file 或 git add .
  添加描述：git commit -m "miao shu nei rong"
  提交数据：git push origin name
  本地仓库与远程仓库关联：git remote add origin git_url
  查看git地址：git remote -v
* 分支操作
  查看分支：git branch
  创建分支：git branch name
  提交分支到远程：git push -u origin name
  切换分支：git checkout name
  创建+切换分支：git checkout -b name
  创建远程分支拷贝+切换分支：git checkout -b name origin/name
  合并某分支到当前分支：git merge name
  删除分支：git branch -d name
  删除远程分支：git push origin :name
* 标签操作
  git tag tag_name commit_id
  git tag 查看标签
  git tag -d tag_name 删除标签
  git push origin tag_name 推送标签到远程仓库
* 合并操作
  git pull:将远程主机的最新内容拉下来后直接合并，即：git pull = git fetch + git merge，这样可能会产生冲突，需要手动解决
  git fetch:将远程主机的最新内容拉到本地，用户在检查了以后决定是否合并到工作本机分支中
  git fetch用法
  git fetch origin branch_name（获取分支更新）
  git log -p FETCH_HEAD(check是否有冲突)
  git merge FETCH_HEAD(merge分支更新)
  git pull用法
  git pull origin remote_branch_name:local_branch_name(冒号后面部分可省略，表示与当前分支合并)

# git flow流程

* 创建开发分支并push到服务器
  git branch dev
  git push origin dev
* 协同开发
  拷贝远程仓库项目
  git clone “git address”
  配置名字及邮箱
  git config user.name “Your name”
  git config user.email “Your email Addr”
  在本地创建一个远程dev分支的副本
  git checkout -b dev origin/dev
  基于dev分支，创建一个功能分支
  git checkout -b feature_name dev
* 功能开发
  查看文件是否修改
  git status
  添加文件(将文件存入暂存区)
  git add “file_name”
  提交文件到暂存区，添加提交描述
  git commit -m “description”
  查看修改之后还未暂存的变化内容
  git diff
  git diff HEAD
  查看暂存的文件和HEAD的差异
  git diff --cached
  git diff --staged
  查看两个分支最新提交的内容差异
  git diff branch1 branch2
* 完成功能
  更新dev分支代码
  git pull origin dev
  切换到dev分支
  git checkout dev
  融合功能分支到dev分支
  git merge feature_name
  push到远程dev分支
  git push origin dev
  删除功能分支
  git branch -d feature_name

# git常用操作

* gitignore 不起作用的解决办法

  * git rm -r --cached . 

  * git add . 

  * git commit -m ”update .gitignore“

* git远程仓库修改

  * git remote set-url origin {remote_url}
  * git branch -m {old_branch_name} {new_branch_name}
  * git push --all
  * git push --tags