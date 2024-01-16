insert into household (device_id, household_id, pricing_plan) values ('D1', 'h1', 'NORMAL');
insert into household (device_id, household_id, pricing_plan) values ('D2', 'h2', 'DAYNIGHT');

insert into household_member (date_of_birth, gender, household_id, device_id, household_member_id, member_name) values ('1985-08-22', 'MALE', 'h2', 'D1', 'm2', 'Bob');
insert into household_member (date_of_birth, gender, household_id, device_id, household_member_id, member_name) values ('1992-02-10', 'MALE', 'h1', 'D2', 'm3', 'Charlie');

insert into household_user (date_of_birth, email_address, gender, household_id, device_id, household_member_id, member_name, user_password, user_role) values ('1995-09-28', 'felix.lahnsteiner@students.fhv.at', 'MALE', 'h2', 'D2', '2', 'Felix Lahnsteiner', 'secret', 'USER');
insert into household_user (date_of_birth, email_address, gender, household_id, device_id, household_member_id, member_name, user_password, user_role) values ('1990-05-15', 'alice@example.com', 'FEMALE', 'h1', 'D1', '1', 'Alice', '79XRn7pTF6sf33S8GGhkwL7gbs5bIAhuUULKmpdEA7U=', 'ADMIN');