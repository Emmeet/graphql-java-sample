CREATE TABLE "t_system_user"
(
    "id"          varchar(32) COLLATE "pg_catalog"."default"  NOT NULL DEFAULT NULL:: character varying,
    "username"    varchar(200) COLLATE "pg_catalog"."default" NOT NULL DEFAULT NULL:: character varying,
    "password"    varchar(200) COLLATE "pg_catalog"."default"          DEFAULT NULL:: character varying,
    "first_name"  varchar(50) COLLATE "pg_catalog"."default",
    "last_name"   varchar(50) COLLATE "pg_catalog"."default",
    "avatar"      varchar(255) COLLATE "pg_catalog"."default",
    "phone"       varchar(50) COLLATE "pg_catalog"."default",
    "active"      bool                                                 DEFAULT true,
    "is_delete"   bool                                                 DEFAULT false,
    "create_by"   varchar(32) COLLATE "pg_catalog"."default",
    "create_date" timestamp(0),
    "update_by"   varchar(32) COLLATE "pg_catalog"."default",
    "update_date" timestamp(0),
    CONSTRAINT "t_system_user_pkey" PRIMARY KEY ("id")
)
;

ALTER TABLE "t_system_user"
    OWNER TO "postgres";

COMMENT
ON COLUMN "t_system_user"."id" IS 'Primary Key';

COMMENT
ON COLUMN "t_system_user"."username" IS 'Username';

COMMENT
ON COLUMN "t_system_user"."password" IS 'Password';

COMMENT
ON COLUMN "t_system_user"."first_name" IS 'First Name';

COMMENT
ON COLUMN "t_system_user"."last_name" IS 'Last Name';

COMMENT
ON COLUMN "t_system_user"."avatar" IS 'Avatar';

COMMENT
ON COLUMN "t_system_user"."phone" IS 'Phone Number';

COMMENT
ON COLUMN "t_system_user"."active" IS 'Active';

COMMENT
ON COLUMN "t_system_user"."is_delete" IS 'Is Delete';

COMMENT
ON COLUMN "t_system_user"."create_by" IS 'Create By';

COMMENT
ON COLUMN "t_system_user"."create_date" IS 'Create Date';

COMMENT
ON COLUMN "t_system_user"."update_by" IS 'Update By';

COMMENT
ON COLUMN "t_system_user"."update_date" IS 'Update Date';

COMMENT
ON TABLE "t_system_user" IS 'User';

INSERT INTO "t_system_user" ("id",
                             "username",
                             "password",
                             "first_name",
                             "last_name",
                             "avatar",
                             "phone",
                             "active",
                             "is_delete",
                             "create_by",
                             "create_date",
                             "update_by",
                             "update_date")
VALUES ('a550e277516ab8ae2a52b469908c0f49',
        'admin',
        '$2a$10$uSOotRmhIrQFOIoKuS5UDukUv3WDAP6VbLSJ0Sa..ocNogB3fzcsa',
        NULL,
        NULL,
        NULL,
        NULL,
        't',
        'f',
        '',
        now(),
        '',
        now());