-- This script was generated by the ERD tool in pgAdmin 4.
-- Please log an issue at https://github.com/pgadmin-org/pgadmin4/issues/new/choose if you find any bugs, including reproduction steps.
BEGIN;


CREATE TABLE IF NOT EXISTS public.audit_log
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    action character varying(255) COLLATE pg_catalog."default" NOT NULL,
    user_id bigint,
    CONSTRAINT audit_log_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.permissions
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    name character varying(50) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT permissions_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.projects
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    priority integer NOT NULL,
    status character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT projects_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.role_permissions
(
    role_id bigint NOT NULL,
    permission_id bigint NOT NULL,
    CONSTRAINT role_permissions_pkey PRIMARY KEY (role_id, permission_id)
);

CREATE TABLE IF NOT EXISTS public.roles
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    name character varying(50) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT roles_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.statuses
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    name character varying(50) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT statuses_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.task_participants
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    role_in_task character varying(255) COLLATE pg_catalog."default" NOT NULL,
    task_id bigint,
    user_id bigint,
    CONSTRAINT task_participants_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.tasks
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    description character varying(255) COLLATE pg_catalog."default" NOT NULL,
    name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    project_id bigint,
    status_id bigint,
    CONSTRAINT tasks_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.users
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    password character varying(100) COLLATE pg_catalog."default" NOT NULL,
    status character varying(255) COLLATE pg_catalog."default" NOT NULL,
    role_id bigint,
    active boolean NOT NULL,
    username character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT users_pkey PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS public.audit_log
    ADD CONSTRAINT fkk4alalwu62gj4tfbgfefll3tu FOREIGN KEY (user_id)
    REFERENCES public.users (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.role_permissions
    ADD CONSTRAINT fkegdk29eiy7mdtefy5c7eirr6e FOREIGN KEY (permission_id)
    REFERENCES public.permissions (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.role_permissions
    ADD CONSTRAINT fkn5fotdgk8d1xvo8nav9uv3muc FOREIGN KEY (role_id)
    REFERENCES public.roles (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.task_participants
    ADD CONSTRAINT fkdya6ba1k26jbo2g73m56e737s FOREIGN KEY (task_id)
    REFERENCES public.tasks (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.task_participants
    ADD CONSTRAINT fkk8of8cdmrownmqej86i0ie813 FOREIGN KEY (user_id)
    REFERENCES public.users (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.tasks
    ADD CONSTRAINT fkhp48vnj340wm1stg2kux5dqcx FOREIGN KEY (status_id)
    REFERENCES public.statuses (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.tasks
    ADD CONSTRAINT fksfhn82y57i3k9uxww1s007acc FOREIGN KEY (project_id)
    REFERENCES public.projects (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.users
    ADD CONSTRAINT fkp56c1712k691lhsyewcssf40f FOREIGN KEY (role_id)
    REFERENCES public.roles (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;

END;