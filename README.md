# mmap测试项目

## 使用docker运行SpringBoot项目

### 安装 Vagrant 相关软件
如果是在 macOS 下，可直接通过 Homebrew 安装 Vagrant/Virtualbox:

```
brew cask install virtualbox
brew cask install vagrant
```
如果需要配置 proxy，需要安装vagrant-proxyconf插件：

```
vagrant plugin install vagrant-proxyconf
```
#### 安装和配置
安装 Vagrant 虚拟机

```
git clone https://github.com/pangff/mmap-test-simple.git
```

在项目根目录下执行：

```
vagrant up
```

将自动完成如下内容：

* 创建 1 台 virtualbox 虚拟机
  * 使用 ubuntu server 18.04 镜像
  * 访问地址为
    * mmap：192.168.100.10

  * 禁用系统 swap
  * 设置时区为中国时区
* 虚拟机安装
  * docker
  * docker-compose
  * jdk1.8
  * maven


#### 运行

登录虚拟机
```
vagrant ssh mmap
```

编译项目，运行
```
cd /vagrant

mvn package && java -jar target/*.jar
```

在虚拟机可以通过curl验证（返回hello world）

```
curl http://localhost:3000
```

```
command+c 结束服务
```

build镜像

```
docker build --build-arg JAR_FILE=target/*.jar -t  pffair/mmap-docker .
```

启动服务
```
docker run -p 3000:3000 pffair/mmap-docker
```

查看容器运行情况
```
docker ps
```

在虚拟机通过curl验证（返回hello world）

```
curl http://localhost:3000
```

## 使用VisualVM 监控java服务


#### 安装visualVM

* 下载地址： https://visualvm.github.io/index.html
* 中文介绍：https://htmlpreview.github.io/?https://raw.githubusercontent.com/visualvm/visualvm.java.net.backup/master/www/zh_CN/intro.html
 
 
#### clone项目切换到m2分支

```
git clone https://github.com/pangff/mmap-test-simple.git

git fetch

git checkout m2
```

#### build mmap服务 镜像并运行

登录虚拟机
```
vagrant ssh mmap
```

编译项目，运行
```
cd /vagrant

mvn package && java -jar target/*.jar
```

在虚拟机可以通过curl验证（返回hello world）

```
curl http://localhost:3000
```

```
command+c 结束服务
```

build镜像

```
docker build --build-arg JAR_FILE=target/*.jar -HOSTNAME=java服务所在服务器IP -t  pffair/mmap-docker .
```

启动服务
```
docker run -p 3000:3000 -p 8888:8888 pffair/mmap-docker
```

查看容器运行情况
```
docker ps
```

在本地客户端机器测试

```
curl http://ServerHOST:3000
```

#### 客户端机器运行visualVM

右键Remote菜单

<img src= "https://github.com/pangff/mmap-test-simple/blob/m2/pic/p1.png" width="250" />

点击Add Remote Host... 。 添加java服务host名称（vagrant ssh mmap 进入 mmap后 ifconfig查看）

<img src= "https://github.com/pangff/mmap-test-simple/blob/m2/pic/p2.png" width="250" />

右键点击添加的java服务host节点

<img src= "https://github.com/pangff/mmap-test-simple/blob/m2/pic/p3.png" width="250" />

添加JMX连接 Add JMX Connection...

<img src= "https://github.com/pangff/mmap-test-simple/blob/m2/pic/p4.png" width="250" />

配置端口 8888、选中Do not require SSL connection选项 点击OK 可以看到java服务监控信息

<img src= "https://github.com/pangff/mmap-test-simple/blob/m2/pic/p5.png" width="250" />

