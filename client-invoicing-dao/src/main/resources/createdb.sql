DROP TABLE IF EXISTS timesheet;
DROP TABLE IF EXISTS emp_proj;
DROP TABLE IF EXISTS project;
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS client;

CREATE TABLE client
(
  client_id bigint NOT NULL,
  client_name character varying(255),
  address_line_1 character varying(255),
  address_line_2 character varying(255),
  address_line_3 character varying(255),
  address_line_4 character varying(255),
  address_postcode character varying(255),
  contact_first_name character varying(255),
  contact_last_name character varying(255),
  contact_email character varying(255),
  contact_telephone character varying(255),
  CONSTRAINT client_pkey PRIMARY KEY (client_id )
);

CREATE TABLE employee
(
  employee_id bigint NOT NULL,
  national_insurance_no character varying(255) UNIQUE,
  dob date,
  first_name character varying(255),
  last_name character varying(255),
  role character varying(255),
  address_line_1 character varying(255),
  address_line_2 character varying(255),
  address_line_3 character varying(255),
  address_line_4 character varying(255),
  address_postcode character varying(255),
  hourly_rate numeric(19,2),
  CONSTRAINT employee_pkey PRIMARY KEY (employee_id )
);

CREATE TABLE project
(
  project_id bigint NOT NULL,
  project_name character varying(255),
  startdate date,
  enddate date,
  client_id bigint,
  CONSTRAINT project_pkey PRIMARY KEY (project_id ),
  CONSTRAINT fk_project_client_id FOREIGN KEY (client_id)
      REFERENCES client (client_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE emp_proj
(
  project_id bigint NOT NULL,
  employee_id bigint NOT NULL,
  CONSTRAINT emp_proj_pkey PRIMARY KEY (project_id , employee_id ),
  CONSTRAINT fk_emp_proj_employee_id FOREIGN KEY (employee_id)
      REFERENCES employee (employee_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_emp_proj_project_id FOREIGN KEY (project_id)
      REFERENCES project (project_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE timesheet
(
  timesheet_id bigint NOT NULL,
  description character varying(255),
  entry_date date,
  hours_worked double precision,
  employee_id bigint,
  project_id bigint,
  CONSTRAINT timesheet_pkey PRIMARY KEY (timesheet_id ),
  CONSTRAINT fk_timesheet_employee_id FOREIGN KEY (employee_id)
      REFERENCES employee (employee_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_timesheet_project_id FOREIGN KEY (project_id)
      REFERENCES project (project_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);