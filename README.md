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