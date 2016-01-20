# sdd-ufg
Sistema de distribuição de disciplinas - UFG

# Instalação

### 1. Baixar o [composer](https://getcomposer.org/doc/00-intro.md)

`curl -sS https://getcomposer.org/installer | php`

OU

`php -r "readfile('https://getcomposer.org/installer');" | php`

### 2. Instalação das dependências via composer:

`mv composer.phar src/`

`cd src/`

`php composer.phar install`

# Executando o projeto

`cd src/`

`./bin/cake server`

Isto irá iniciar o servidor web em http://localhost:8765/. Caso você deseje muder a porta ou o host do servidor basta utilizar os parâmetros -H e -p:

Ex: `./bin/cake server -H 192.168.13.37 -p 5673`
