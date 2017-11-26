connect 'localhost:C:\Users\Admin\Documents\NetBeansProjects\FitnessClub-master\resource\CLUBDB.fdb'  user 'SYSDBA' password 'masterkey';
drop database;
commit;

create database 'localhost:C:\Users\Admin\Documents\NetBeansProjects\FitnessClub-master\resource\CLUBDB.fdb' user 'SYSDBA' password 'masterkey';
commit;

connect 'localhost:C:\Users\Admin\Documents\NetBeansProjects\FitnessClub-master\resource\CLUBDB.fdb'  user 'SYSDBA' password 'masterkey';

input "C:\Users\Admin\Documents\NetBeansProjects\FitnessClub-master\resource\db_create.sql";
input "C:\Users\Admin\Documents\NetBeansProjects\FitnessClub-master\resource\db_fill_appstate.sql";