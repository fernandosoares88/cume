# Essa aplicação foi desenvolvida para gerenciar solicitações de almoço no refeitorio do IFRN-Lajes
Para executar a aplicação na sua própria máquina, execute as seguintes instruções:

1. Baixe o repositório e importe no eclipse como 'Existent maven project'.
2. Crie o banco de dados da aplicação: 
> 'create database cume;'

3. Altere a configuração de senha do banco no arquivo 'Application.properties' para corresponder a senha que você utiliza no banco de dados instalado na sua máquina.
4. Execute a aplicação para a criação automática das tabelas do banco de dados.
5. Execute os seguintes comandos no seu banco de dados para criar os dados básicos necessários. Esses comandos criam um usuário administrador com matrícula 'admin' e senha '123'.

> use cume;
> 
> insert into role (name) values ("ROLE_ADMIN"), ("ROLE_STUDENT"), ("ROLE_ASAES"), ("ROLE_COORD");
>
> insert into user (dtype, name, password, registration) values('PublicServer', 'Usuário Administrador', '$2a$10$fk/vcwu/2kq0KX3lAV703uAmlv4tNNlh.7Zp6tXddeqeDeDh77wC6', 'admin');
>
> insert into user_roles (user_id, roles_id) values (1, 1);
>
> insert into clazz (name) values ('INFOINT1VA'), ('INFOINT1VB'), ('INFOINT2VA'), ('INFOINT2VB');
