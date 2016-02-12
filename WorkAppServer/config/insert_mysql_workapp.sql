use workapp;

// Inserting values into status table
insert into `status` values('0', 'todo');
insert into `status` values('1', 'inprogress');
insert into `status` values('2', 'done');
insert into `status` values('3', 'behind_schedule');
insert into `status` values('4', 'on_track');
insert into `status` values('5', 'stalled');

// Inserting values into role table
insert into `role` values('0', 'owner');
insert into `role` values('1', 'admin');
insert into `role` values('2', 'editor');
insert into `role` values('3', 'reader');

// Inserting values into feed_type table
insert into `feed_type` values('0', 'Board Created');
insert into `feed_type` values('1', 'Board Expired');
insert into `feed_type` values('2', 'Work Created');
insert into `feed_type` values('3', 'Work Expired');
insert into `feed_type` values('4', 'Work Edited');
insert into `feed_type` values('5', 'Comment Added');
insert into `feed_type` values('6', 'Comment Removed');
insert into `feed_type` values('7', 'Team Member Added');
insert into `feed_type` values('8', 'Team Member Removed');
insert into `feed_type` values('9', 'Reminder');
insert into `feed_type` values('10', 'Task Recommendation');
insert into `feed_type` values('11', 'Talent Recommendation');
insert into `feed_type` values('12', 'Assistant');

// Inserting values into occupation_catalog table
insert into `occupation_catalog` values('0', 'Personal');
insert into `occupation_catalog` values('1', 'Brainstorming');
insert into `occupation_catalog` values('2', 'Meeting');
insert into `occupation_catalog` values('3', 'Event');
insert into `occupation_catalog` values('4', 'Software Engineering');
insert into `occupation_catalog` values('5', 'Financial Auditing');
insert into `occupation_catalog` values('6', 'Legal Service');
insert into `occupation_catalog` values('7', 'Investment');
insert into `occupation_catalog` values('8', 'Business');
insert into `occupation_catalog` values('9', 'Management');
insert into `occupation_catalog` values('10', 'Marketing');
insert into `occupation_catalog` values('11', 'Sales');
insert into `occupation_catalog` values('12', 'Others');
insert into `occupation_catalog` values('13', 'Software Operations');
insert into `occupation_catalog` values('14', 'Do-It-Yourself');
insert into `occupation_catalog` values('15', 'Dancing');
insert into `occupation_catalog` values('16', 'Singing');
insert into `occupation_catalog` values('17', 'Arts & Craft');
insert into `occupation_catalog` values('18', 'Photography');
insert into `occupation_catalog` values('19', 'Painting');
insert into `occupation_catalog` values('20', 'Drilling');
insert into `occupation_catalog` values('21', 'Plumbing');
insert into `occupation_catalog` values('22', 'Pilot');
insert into `occupation_catalog` values('23', 'Driving');
insert into `occupation_catalog` values('24', 'Dining');
insert into `occupation_catalog` values('25', 'School Home Work');
insert into `occupation_catalog` values('26', 'Teaching');

// Inserting values into skill table
insert into `skill` values('0', '4', 'Backend');
insert into `skill` values('1', '4', 'Frontend');
insert into `skill` values('2', '4', 'Scalability');
insert into `skill` values('3', '4', 'High Performance');
insert into `skill` values('4', '4', 'Java');
insert into `skill` values('5', '4', 'C++');
insert into `skill` values('6', '4', 'C');
insert into `skill` values('7', '4', 'Web App');
insert into `skill` values('8', '4', 'Android App');
insert into `skill` values('9', '4', 'iOS App');
insert into `skill` values('10', '4', 'JavaScript');
insert into `skill` values('11', '4', 'NodeJS');
insert into `skill` values('12', '4', 'SQL');
insert into `skill` values('13', '4', 'Big Data');
insert into `skill` values('14', '4', 'Algorithms');
insert into `skill` values('15', '4', 'Virtual Reality');
insert into `skill` values('16', '4', 'Virtual Assistant');
insert into `skill` values('17', '4', 'Scripting');
insert into `skill` values('0', '13', 'Monitoring & Alert');
insert into `skill` values('1', '13', 'Production Support');
insert into `skill` values('2', '13', 'Documentation');

// Inserting values into milestone table
insert into `milestone` (id,occupation_id,name) values('0', '4', 'Design');
insert into `milestone` (id,occupation_id,name) values('1', '4', 'Implementation');
insert into `milestone` (id,occupation_id,name) values('2', '4', 'Testing');
insert into `milestone` (id,occupation_id,name) values('3', '4', 'Release');
insert into `milestone` (id,occupation_id,name) values('4', '4', 'Bug Fixes');
insert into `milestone` (id,occupation_id,name) values('0', '13', 'Monitoring');
insert into `milestone` (id,occupation_id,name) values('1', '13', 'P0 Support');
insert into `milestone` (id,occupation_id,name) values('2', '13', 'P1 Support');
insert into `milestone` (id,occupation_id,name) values('3', '13', 'P2 Support');
insert into `milestone` (id,occupation_id,name) values('4', '13', 'P3 Support');
