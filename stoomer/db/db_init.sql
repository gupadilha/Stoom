-- mysql db creation
create database stoomerdb;
create user 'stoomer'@'%' identified by 'StoomerSecrets';
grant all on stoomerdb.* to 'stoomer'@'%';

