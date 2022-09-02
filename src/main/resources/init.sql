# --------------------------------------------------------
# --  DDL for schema eshop
# --------------------------------------------------------
drop schema if exists eshop;
create schema if not exists eshop;
#
# --------------------------------------------------------
# --  DDL for Table categories
# --------------------------------------------------------
drop table if exists eshop.categories;
create table if not exists eshop.categories
(
    id     int         not null auto_increment,
    name   varchar(45) not null,
    rating double      not null,
    primary key (id),
    unique index idx_categories_category_id_unique (id asc),
    unique index idx_categories_name_unique (name asc)
);
# --------------------------------------------------------
# --  DDL for Table products
# --------------------------------------------------------
drop table if exists eshop.products;
create table if not exists eshop.products
(
    id                    int           not null auto_increment,
    name                  varchar(700)  not null,
    description           varchar(1500) not null,
    price_before_discount decimal(7, 2) not null,
    discount              decimal(7, 0) not null,
    price                 decimal(7, 2) not null,
    rating                double        not null,
    category_id           int           not null,
    primary key (id),
    unique index idx_products_id_unique (id asc),
    constraint fk_products_category_id_categories_id
        foreign key (category_id)
            references eshop.categories (id)
            on delete cascade
            on update cascade
);

#--------------------------------------------------------
--  DDL for Table PRODUCT_IMAGES
#--------------------------------------------------------
drop table if exists eshop.images;
create table if not exists eshop.images
(
    id           int          not null auto_increment,
    category_id  int,
    product_id   int,
    primary_flag boolean      not null,
    image_path   varchar(200) not null,
    primary key (id),
    constraint fk_images_category_id_categories_id
        foreign key (category_id) references eshop.categories (id)
            on delete cascade
            on update cascade,
    constraint fk_images_product_id_products_id
        foreign key (product_id) references eshop.products (id)
            on delete cascade
            on update cascade
);

# --------------------------------------------------------
# --  DDL for Table roles
# --------------------------------------------------------
drop table if exists eshop.roles;
create table if not exists eshop.roles
(
    id   int          not null auto_increment,
    name varchar(200) not null,
    primary key (id),
    unique index idx_roles_id_unique (id asc),
    unique index idx_roles_name_unique (name asc)
);

# --------------------------------------------------------
# --  DDL for Table users
# --------------------------------------------------------
drop table if exists eshop.users;
create table if not exists eshop.users
(
    id         int          not null auto_increment,
    name       varchar(45)  not null,
    surname    varchar(45)  not null,
    email      varchar(45)  not null,
    password   varchar(400) not null,
    login      varchar(45)  not null,
    birth_date date,
    balance    decimal(7, 2),
    role_id    int          not null,
    primary key (id),
    unique index idx_users_user_id_unique (id asc),
    unique index idx_users_login_unique (login asc),
    constraint fk_users_role_id_roles_id
        foreign key (role_id)
            references eshop.roles (id)
            on delete cascade
            on update cascade
);

# --------------------------------------------------------
# --  DDL for Table orders
# --------------------------------------------------------
drop table if exists eshop.orders;
create table if not exists eshop.orders
(
    id         int           not null auto_increment,
    price      decimal(7, 2) not null,
    date_order date          not null,
    user_id    int           not null,
    primary key (id),
    unique index idx_orders_id_unique (id asc),
    constraint fk_orders_user_id_users_id
        foreign key (user_id)
            references eshop.users (id)
            on delete cascade
            on update cascade
);
# --------------------------------------------------------
# --  DDL for Table order_products
# --------------------------------------------------------
drop table if exists eshop.order_products;
create table if not exists eshop.order_products
(
    order_id         int not null,
    product_id       int not null,
    product_quantity int not null,
    primary key (order_id, product_id),
    constraint fk_orders_products_order_id_orders_id
        foreign key (order_id)
            references orders (id)
            on delete cascade
            on update cascade,
    constraint fk_orders_products_product_id_products_id
        foreign key (product_id)
            references products (id)
            on delete cascade
            on update cascade
);

#------------------------------------------------------
#--  DML for Table eshop.categories
#--------------------------------------------------------
insert into eshop.categories (id, name, rating)
values (1, 'Laptops', 4.8);
insert into eshop.categories (id, name, rating)
values (2, 'Monitors', 4.7);
insert into eshop.categories (id, name, rating)
values (3, 'Pads', 4.2);
insert into eshop.categories (id, name, rating)
values (4, 'Printers', 4.2);
insert into eshop.categories (id, name, rating)
values (5, 'Scanners', 4.6);
insert into eshop.categories (id, name, rating)
values (6, 'E-books', 4.1);
insert into eshop.categories (id, name, rating)
values (7, 'Smartphones', 4.7);
insert into eshop.categories (id, name, rating)
values (8, 'Smartwatches', 4.3);
insert into eshop.categories (id, name, rating)
values (9, 'Headphones', 4.6);
insert into eshop.categories (id, name, rating)
values (10, 'Washing machines', 4.4);
insert into eshop.categories (id, name, rating)
values (11, 'Irons', 5);
insert into eshop.categories (id, name, rating)
values (12, 'Vacuum cleaners', 4.7);
insert into eshop.categories (id, name, rating)
values (13, 'Fridges', 4);
insert into eshop.categories (id, name, rating)
values (14, 'Cookers', 4.3);
insert into eshop.categories (id, name, rating)
values (15, 'Kitchen hoods', 4.7);
insert into eshop.categories (id, name, rating)
values (16, 'Dishwashers', 4.2);
insert into eshop.categories (id, name, rating)
values (17, 'TVs', 4.9);
insert into eshop.categories (id, name, rating)
values (18, 'Sports simulators', 4.3);
insert into eshop.categories (id, name, rating)
values (19, 'Photocameras', 4.4);
insert into eshop.categories (id, name, rating)
values (20, 'Videocameras', 4.8);


#------------------------------------------------------
#--  DML for Table eshop.products
#--------------------------------------------------------
#----laptops
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (1, 'Honor MagicBook X14 NBR-WAI9 (5301AAPL)',
        'Screen:14.0'''', 1920x1080 px, IPS 60 Hz, 250 fr/m2;
        Processor: Intel Core-i3 10110U 2 core, 2.1 HHz - 4.1 HHz, Comet Lake;
        Video card:Intel UHD Graphics;
        Memory: 8 GB RAM DDR4, SSD 256 GB;
        Operating system: Windows 10 Home;
        Weight and dimensions: 1.38 kg, 322.5 mm х 215 mm х 15.9 mm'
           , 2125, 20, 1700, 4.5, 1);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (2, 'Lenovo IdeaPad 3 15IGL05 81WQ0023RE',
        'Screen: 15.6'''', 1920x1080 px, TN+Film 60 Hz;
        Processor: Intel Celeron N4020 2 core, 1.1 HHz - 2.8 HHz, Gemini Lake R;
        Video card:Intel UHD Graphics 600;
        Memory: 4 GB RAM DDR4, SSD 256 GB;
        Weight and dimensions: 1.7 kg, 362.2 mm х 253.4 mm х 19.9 mm'
           , 1572, 30, 1100, 4.3, 1);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (3, 'Haier U1520SD',
        'Screen: 15.6'''', 1920x1080 px, IPS 60 Hz;
        Processor: Intel Celeron N4020 1 core, 1.1 HHz - 2.8 HHz, Gemini Lake;
        Video card:Intel UHD Graphics 600;
        Memory: 4 GB RAM LPDDR4, SSD 128 GB;
        Weight and dimensions: 1.5 kg, 375 mm х 240 mm х 20 mm'
           , 1269, 35, 825, 4.3, 1);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (4, 'Asus VivoBook S14 S433EA-AM464',
        'Screen: 14.0'''', 1920x1080 px, IPS 60 Hz;
        Processor: Intel Core-i5 1135G7 4 core, 2.4 HHz- 4.2 HHz, Tiger Lake;
        Video card:Intel Iris Xe Graphics;
        Memory: 8 GB RAM DDR4, SSD 512 GB;
        Weight and dimensions: 1.4 kg, 324.9 mm х 213.5 mm х 15.9 mm'
           , 2297, 0, 2297, 4.7, 1);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (5, 'HP 15s-eq2115nw (4Y0U8EA)',
        'Screen: 15.6'''', 1920x1080 px, IPS 60 Gz;
        Processor: AMD Ryzen 3 5300U 4 core, 2.6 HHz - 3.8 HHz, Lucienne;
        Video card:AMD Radeon Graphics;
        Memory: 8 GB RAM DDR4, SSD 256 GB;
        Weight and dimensions: 1.69 kg, 358.5 mm х 242 mm х 17.9 mm'
           , 1855, 0, 1855, 4.7, 1);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (6, 'HP Victus 16-e0154nw',
        'Screen: 16.1'''', 1920x1080 px, IPS 144 Hz;
        Processor: AMD Ryzen 5 5600H 6 core, 3.3 HHz- 4.2 HHz, Cezanne;
        Video card:NVIDIA GeForce RTX 3050 4 GB;
        Memory: 16 GB RAM DDR4, SSD 512 GB;
        Weight and dimensions: 2.48 kg, 370 mm х 260 mm х 23.5 mm'
           , 3989, 0, 3989, 4.7, 1);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (7, 'Asus E210MA-GJ365',
        'Screen: 11.6'''', 1366x768 px, TN+Film 60 Hz;
        Processor: Intel Celeron N4020 2 core, 1.1 HHz- 2.8 HHz, Gemini Lake;
        Video card:Intel UHD Graphics 600;
        Memory: 4 GB RAM LPDDR4, SSD 256 GB;
        Weight and dimensions: 1.05 kg, 279 mm х 191 mm х 16.9 mm'
           , 1156, 20, 925, 4.7, 1);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (8, 'Lenovo IdeaPad 3 15IML05 81WB00QXRE',
        'Screen: 15.6'''', 1920x1080 px, TN+Film 60 Hz;
        Processor: Intel Celeron 5205U 2 core, 1.9 HHz - 1.9 HHz, Comet Lake;
        Video card:Intel UHD Graphics;
        Operating system: Windows 10 Home;
        Memory: 4 GB RAM LPDDR4, SSD 256 GB;
        Weight and dimensions: 1.7kg, 362.2 mm х 253.4 mm х 19.9 mm'
           , 1222, 0, 1222, 4.3, 1);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (9, 'Lenovo V14 GEN2 ALC 82KC003NRU',
        'Screen: 14.0'''', 1920x1080 px, TN+Film 60 Hz;
        Processor: AMD Ryzen 5 5500U 6 core, 2.1 HHz - 4.0 HHz, Lucienne;
        Video card:AMD Radeon Graphics;
        Memory: 8 GB RAM DDR4, SSD 256 GB;
        Weight and dimensions: 1.6 kg, 324.2 mm х 215.2 mm х 19.9 mm'
           , 1899, 0, 1899, 4.3, 1);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (10, 'Lenovo IdeaPad 5 15ALC05 82LN00T5RE',
        'Screen: 15.6'''', 1920x1080 px, IPS 60 Hz;
        Processor: AMD Ryzen 5 5500U 6 core, 2.1 HHz - 4.0 HHz, Lucienne;
        Video card:AMD Radeon Graphics;
        Memory: 16 GB RAM DDR4, SSD 256 GB;
        Weight and dimensions: 1.66 kg, 356.67 mm х 233.13 mm х 17.9-19.9 mm'
           , 3318, 20, 2655, 4.7, 1);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (11, 'Acer Aspire 3 A315-56-55RH (NX.HS5EU.029)',
        'Screen: 15.6'''', 1920x1080 px, IPS 60 Hz;
        Processor: Intel Core-i5 1035G1 4 core, 1.0 HHz - 3.6 HHz, Ice Lake;
        Video card: Intel UHD Graphics;
        Memory: 8 GB RAM DDR4, SSD 256 GB;
        Weight and dimensions: 1.9 kg, 363.4 mm х 247.5 mm х 19.9 mm'
           , 1997, 0, 1997, 4.3, 1);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (12, 'Acer Nitro 5 AN517-54-507Y',
        'Screen: 17.3'''', 1920x1080 px, IPS 144 Hz;
        Processor: Intel Core-i5 11400H 6 core, 2.7 HHz - 4.5 HHz, Tiger Lake;
        Video card: NVIDIA GeForce RTX 3050 Ti 4 GB GDDR6;
        Memory: 16 GB RAM DDR4, SSD 512 GB;
        Weight and dimensions: 2.2 kg, 403.5 mm х 280 mm х 24.9 mm'
           , 3897, 0, 4897, 4.7, 1);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (13, 'Lenovo IdeaPad L3 15ITL6 82HL006KRE',
        'Screen: 15.6'''', 1920x1080 px, IPS 60 Hz;
        Processor: Intel Core-i5 1135G7 4 core, 2.4 HHz - 4.2 HHz, Tiger Lake;
        Video card: Intel Iris Xe Graphics;
        Memory: 8 GB RAM DDR4, SSD 256 GB;
        Weight and dimensions: 2.2 kg, 363 mm х 254.6 mm х 22.9 mm'
           , 2470, 15, 2099, 4.3, 1);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (14, 'Acer Swift 1 SF114-34-P9YY (NX.A77EU.00Y)',
        'Screen: 14.0'''', 1920x1080 px, IPS 60 Hz;
        Processor: Intel Pentium Silver N6000 4 core, 1.1 HHz - 3.3 HHz, Jasper Lake;
        Video card: Intel UHD Graphics;
        Memory: 8 GB RAM LPDDR4X, SSD 256 GB;
        Weight and dimensions: 1.3 kg, 322.8 mm х 212 mm х 15 mm'
           , 1655, 0, 1655, 4.3, 1);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (15, 'Asus E410MA-BV1517',
        'Screen: 14.0'''', 1366x768 px, TN+Film 60 Hz;
        Processor: Intel Celeron N4020 2 core, 1.1 HHz - 2.8 HHz, Gemini Lake R;
        Video card: Intel UHD Graphics 600;
        Memory: 4 GB RAM LPDDR4, SSD 256 GB;
        Weight and dimensions: 1.3 kg, 325 mm х 217 mm х 18.4 mm'
           , 1123, 15, 955, 4.3, 1);

#----monitors
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (16, 'LG 24MK600M-B',
        'Screen diagonal: 23.8;
        Matrix type: IPS;
        Screen resolution: 1920x1080 pixel;
        Response time: 5 ms;
        Screen refresh rate: 75 Hz'
           , 932, 15, 699, 4.3, 2);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (17, 'Gigabyte Aorus FI27Q',
        'Screen diagonal: 27'''';
        Matrix type: IPS;
        Screen resolution: 2560x1440 pixel;
        Response time: 1 ms;
        Screen refresh rate: 165 Hz'
           , 1550, 0, 1550, 4.7, 2);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (18, 'LG 27MK600M-B',
        'Screen diagonal: 27'''';
        Matrix type: IPS;
        Screen resolution: 1920x1080 pixel;
        Response time: 5 ms;
        Screen refresh rate: 75 Hz'
           , 999, 15, 799, 4.7, 2);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (19, 'Samsung S24R350FZI (LS24R350FZIXCI)',
        'Screen diagonal: 23.8'''';
        Matrix type: IPS;
        Screen resolution: 1920x1080 pixel;
        Response time: 5 ms;
        Screen refresh rate: 75 Hz'
           , 656, 0, 656, 4.3, 2);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (20, 'Philips 223V5LSB/00 21.5',
        'Screen diagonal: 21.5'''';
        Matrix type: TN+Film;
        Screen resolution: 1920x1080 pixel;
        Response time: 5 ms;
        Screen refresh rate: 75 Hz'
           , 488, 10, 439, 4.3, 2);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (21, 'AOC C27G2ZU/BK',
        'Screen diagonal: 27'''';
        Matrix type: VA;
        Screen resolution: 1920x1080 pixel;
        Response time: 5 ms;
        Screen refresh rate: 240 Hz'
           , 1465, 15, 1245, 4.7, 2);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (22, 'LG UltraWide 34WP500-B',
        'Screen diagonal: 34'''';
        Matrix type: IPS;
        Screen resolution: 2560x1080 pixel;
        Response time: 5 ms;
        Screen refresh rate: 75 Hz'
           , 1199, 0, 1199, 4.3, 2);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (23, 'ASUS VP229HE',
        'Screen diagonal: 21.5'''';
        Matrix type: IPS;
        Screen resolution: 1920x1080 pixel;
        Response time: 5 ms;
        Screen refresh rate: 60 Hz'
           , 539, 0, 539, 4.3, 2);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (24, 'Philips 241V8L/01',
        'Screen diagonal: 23.8'''';
        Matrix type: VA;
        Screen resolution: 1920x1080 pixel;
        Response time: 4 ms;
        Screen refresh rate: 75 Hz'
           , 579, 0, 579, 4.3, 2);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (25, 'AOC 24B2XHM2',
        'Screen diagonal: 23.8'''';
        Matrix type: VA;
        Screen resolution: 1920x1080 pixel;
        Response time: 4 ms;
        Screen refresh rate: 75 Hz'
           , 649, 0, 649, 4.3, 2);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (26, 'ViewSonic VA3456-MHDJ',
        'Screen diagonal: 34'''';
        Matrix type: IPS;
        Screen resolution: 3440х1440 pixel;
        Response time: 4 ms;
        Screen refresh rate: 75 Hz'
           , 1483, 0, 1483, 4.7, 2);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (27, 'HP V27i (9SV94AA)',
        'Screen diagonal: 27'''';
        Matrix type: IPS;
        Screen resolution: 1920x1080 pixel;
        Response time: 5 ms;
        Screen refresh rate: 60 Hz'
           , 679, 0, 679, 4.3, 2);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (28, 'Lenovo G27q-20 (66C3GAC1EU)',
        'Screen diagonal: 27'''';
        Matrix type: IPS;
        Screen resolution: 2560x1440 pixel;
        Response time: 1 ms;
        Screen refresh rate: 165 Hz'
           , 1630, 15, 1385, 4.7, 2);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (29, 'ASUS TUF Gaming VG259QM',
        'Screen diagonal: 24.5'''';
        Matrix type: IPS;
        Screen resolution: 1920x1080 pixel;
        Response time: 1 ms;
        Screen refresh rate: 280 Hz'
           , 1570, 0, 1570, 4.7, 2);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (30, 'Samsung F27T352FHI',
        'Screen diagonal: 27'''';
        Matrix type: IPS;
        Screen resolution: 1920x1080 pixel;
        Response time: 5 ms;
        Screen refresh rate: 75 Hz'
           , 895, 0, 895, 4.3, 2);

#----pads
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (31, 'Lenovo Tab M10 FHD Plus TB-X606X 64GB LTE ZA5V0083UA (grey)',
        'Screen: 10.3'''' IPS 1920x1200 pixel;
        Processor: MediaTek Helio P22T;
        Operating system: Android 9.0;
        Memory: 4 GB RAM, 64 GB;
        Number of cores: 8;
        4G LTE: Yes'
           , 789, 0, 789, 4.3, 3);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (32, 'Samsung Galaxy Tab A8 LTE SM-X205 32GB (SM-X205NZAACAU) (grey)',
        'Screen: 10.5'''' IPS 1920x1200 pixel;
        Processor: Unisoc Tiger T618;
        Operating system: Android 11;
        Memory: 3 GB RAM, 32 GB;
        Number of cores: 8;
        4G LTE: Yes'
           , 999, 15, 849, 4.7, 3);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (33, 'Lenovo Tab M8 TB-8505X 2GB/32GB LTE (ZA5H0073UA)',
        'Screen: 8'''' IPS 1280x800 pixel;
        Processor: MediaTek Helio A22;
        Operating system: Android 9.0;
        Memory: 2 GB RAM, 32 GB;
        Number of cores: 4;
        4G LTE: Yes'
           , 588, 15, 499, 4.3, 3);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (34, 'Huawei MatePad LTE 4GB/64GB (BAH4-L09) dark-grey',
        'Screen: 10.4'''' IPS 2000х1200 pixel;
        Processor: HiSilicon Kirin 710A;
        Operating system: HarmonyOS 2.0;
        Memory: 4 GB RAM, 64 GB;
        Number of cores: 8;
        4G LTE: Yes'
           , 899, 0, 899, 4.3, 3);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (35, 'Lenovo Tab P11 Pro TB-J706L 128GB LTE ZA7D0074UA (platinum grey)',
        'Screen: 11.6'''' OLED 2560x1600 pixel;
        Processor: Qualcomm Snapdragon 730G;
        Operating system: Android 10;
        Memory: 6 GB RAM, 128 GB;
        Number of cores: 8;
        4G LTE: Yes'
           , 2588, 10, 2319, 4.7, 3);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (36, 'TCL 10 TabMax 4G 9295G 4GB/64GB (frosty blue)',
        'Screen: 10.36'''' IPS 2000х1200 pixel;
        Processor: MediaTek MT8788A;
        Memory: 4 GB RAM, 64 GB;
        Number of cores: 8;
        4G LTE: Yes'
           , 789, 0, 789, 4.3, 3);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (37, 'TCL Tab 10S Wi-Fi 9081X 4GB/64GB (blue)',
        'Screen: 10.1'''' IPS 1920x1200 pixel;
        Processor: MediaTek MT8768;
        Operating system: Android 12;
        Memory: 4 GB RAM, 64 GB;
        Number of cores: 8;
        4G LTE: Yes'
           , 952, 15, 809, 4.3, 3);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (38, 'Prestigio SmartKids PMT3197_W_D_PK (pink)',
        'Screen: 7'''' IPS 1024x600 pixel;
        Processor: Rockchip RK3126C;
        Operating system: Android 8.1 Oreo;
        Memory: 1 GB RAM, 16 GB;
        Number of cores: 4'
           , 307, 30, 215, 4.3, 3);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (39, 'Prestigio Node A8 (PMT4208_3G_E_RU) grey',
        'Screen: 8'''' IPS 1280x800 pixel;
        Processor: Spreadtrum SC7731;
        Operating system: Android 10.1;
        Memory: 1 GB RAM, 32 GB;
        Number of cores: 4'
           , 369, 0, 369, 4.3, 3);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (40, 'Vertex X8 (black)',
        'Screen: 8'''' IPS 1280x800 pixel;
        Processor: Spreadtrum SC9832E;
        Operating system: Android 11;
        Memory: 2 GB RAM, 16 GB;
        Number of cores: 4'
           , 289, 0, 289, 4.3, 3);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (41, 'TCL 10 TabMax 9296G 4GB/64GB (grey)',
        'Screen: 10.36'''' IPS 2000х1200 pixel;
        Processor: MediaTek MT8788A;
        Operating system: Android 10.1;
        Memory: 4 GB RAM, 64 GB;
        Number of cores: 8'
           , 739, 0, 739, 4.7, 3);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (42, 'TCL Tab 10S 4G 9080G 3GB/32GB (grey)',
        'Screen: 10.1'''' IPS 1920x1200 pixel;
        Processor: MediaTek MT8768;
        Operating system: Android 10.1;
        Memory: 3 GB RAM, 32 GB;
        Number of cores: 8'
           , 759, 0, 759, 4.3, 3);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (43, 'BQ-Mobile BQ-1083G Armor PRO PLUS 8GB 3G (Print 8)',
        'Screen: 10.1'''' IPS 1280x800 pixel;
        Processor: Spreadtrum SC7731C;
        Operating system: Android 7.0 Nougat;
        Memory: 1 GB RAM, 8 GB;
        Number of cores: 4'
           , 440, 20, 352, 4.3, 3);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (44, 'BQ-Mobile BQ-7038G Light Plus 16GB 3G (black)',
        'Screen: 7'''' TN+Film 1024x600 pixel;
        Processor: Spreadtrum SC7731E;
        Operating system: Android 9.0;
        Memory: 2 GB RAM, 16 GB;
        Number of cores: 4'
           , 234, 0, 234, 4.3, 3);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (45, 'BQ-Mobile BQ-1085L Hornet Max Pro (black)',
        'Screen: 10.1'''' IPS 1280x800 pixel;
        Processor: Spreadtrum SC9832E;
        Operating system: Android 8.1 Oreo;
        Memory: 2 GB RAM, 16 GB;
        Number of cores: 4'
           , 393, 0, 393, 4.3, 3);

#----printers
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (46, 'Canon PIXMA TS304',
        'Print type: Color;
        Printing technology: Inkjet;
        Connection interfaces: WiFi, USB, Bluetooth'
           , 385, 0, 385, 4.7, 4);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (47, 'Pantum P2200',
        'Print type: Black and white;
        Printing technology: Laser;
        Connection interfaces: USB;
        Double-sided printing: Manual'
           , 376, 20, 301, 4.3, 4);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (48, 'Canon PIXMA G540 (4621C009)',
        'Print type: Color;
        Printing technology: Inkjet;
        Display: Yes;
        Connection interfaces: WiFi, USB, Ethernet'
           , 859, 0, 859, 4.3, 4);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (49, 'HP Color Laser Printer 150nw (4ZB95A)',
        'Print type: Color;
        Printing technology: Laser;
        Connection interfaces: WiFi, USB, Bluetooth'
           , 1661, 10, 1495, 4.7, 4);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (50, 'PANTUM P2507',
        'Print type: Black and white;
        Printing technology: Laser;
        Connection interfaces: USB'
           , 339, 0, 339, 4.3, 4);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (51, 'HP Laser 107a (4ZB77A)',
        'Print type: Black and white;
        Printing technology: Laser;
        Connection interfaces: USB;
        Double-sided printing: Manual'
           , 579, 0, 579, 4.3, 4);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (52, 'CANON I-SENSYS LBP352X',
        'Print type: Black and white;
        Printing technology: Laser;
        Display: Yes;
        Connection interfaces: USB, Ethernet;
        Double-sided printing: Manual'
           , 7625, 0, 7625, 4.7, 4);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (53, 'HP Laser 107w',
        'Print type: Black and white;
        Printing technology: Laser;
        Connection interfaces: USB, Ethernet;
        Double-sided printing: Manual'
           , 716, 15, 609, 4.3, 4);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (54, 'Pantum P2516',
        'Print type: Black and white;
        Printing technology: Laser;
        Connection interfaces: USB'
           , 612, 0, 612, 4.3, 4);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (55, 'HP LaserJet Pro M404n (W1A52A)',
        'Print type: Black and white;
        Printing technology: Laser;
        Display: Yes;
        Connection interfaces: USB, Ethernet;
        Double-sided printing: Manual'
           , 995, 0, 995, 4.7, 4);

#----scanners
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (56, 'CANON imageFORMULA P-215II',
        'Connection interfaces: USB;
        Type of scanner: Plangent;
        Max scan resolution: 600x600 dpi'
           , 1902, 0, 1902, 4.3, 5);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (57, 'Canon imageFORMULA DR-S130 (4812C001)',
        'Connection interfaces: USB, WiFi;
        Type of scanner: Plangent;
        Max scan resolution: 600x600 dpi'
           , 3753, 0, 3753, 4.7, 5);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (58, 'Canon imageFORMULA DR-C225W II',
        'Connection interfaces: USB, WiFi;
        Type of scanner: Plangent;
        Max scan resolution: 600x600 dpi'
           , 2588, 0, 2588, 4.3, 5);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (59, 'CANON imageFORMULA DR-C230',
        'Connection interfaces: USB;
        Type of scanner: Plangent;
        Max scan resolution: 600x600 dpi'
           , 3105, 0, 3105, 4.7, 5);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (60, 'CANON DR-M160II',
        'Connection interfaces: USB;
        Type of scanner: Plangent;
        Max scan resolution: 600x600 dpi'
           , 5812, 0, 5812, 4.7, 5);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (61, 'CANON DR-C240',
        'Connection interfaces: USB;
        Type of scanner: Plangent;
        Max scan resolution: 600x600 dpi'
           , 4889, 10, 4400, 4.3, 5);
insert into eshop.products(id, name, description, price_before_discount, discount, price, rating, category_id)
values (62, 'CANON imageFORMULA DR-M260',
        'Connection interfaces: USB;
        Type of scanner: Plangent;
        Max scan resolution: 600x600 dpi'
           , 4940, 0, 4940, 4.7, 5);

#----ebooks
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (63, 'PocketBook 617 Black (PB617-P-CIS)',
        'Screen size: 6'''';
        Screen technology: E-Ink Carta;
        Illumination: Yes'
           , 446, 15, 379, 4.7, 6);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (64, 'Amazon Kindle Touch 2019 8GB (black)',
        'Screen size: 6'''';
        Screen technology: E-Ink Pearl;
        Illumination: Yes;
        Touch screen: Yes'
           , 319, 0, 319, 4.3, 6);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (65, 'PocketBook 616 (silver)',
        'Screen size: 6'''';
        Screen technology: E-Ink Carta;
        Illumination: Yes'
           , 349, 0, 349, 4.3, 6);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (66, 'Amazon Kindle Touch 2019 8GB (white)',
        'Screen size: 6'''';
        Screen technology: E-Ink Pearl;
        Illumination: Yes;
        Touch screen: Yes'
           , 378, 20, 303, 4.3, 6);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (67, 'Ritmix RBK-617 (black)',
        'Screen size: 6'''';
        Screen technology: E-Ink Pearl;
        Illumination: Yes'
           , 215, 0, 215, 4.3, 6);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (68, 'Amazon Kindle Paperwhite 8GB (sage)',
        'Screen size: 6'''';
        Screen technology: E-Ink Carta;
        Illumination: Yes;
        Touch screen: Yes'
           , 384, 0, 384, 4.3, 6);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (69, 'PocketBook 632 Copper (PB632-K-CIS)',
        'Screen size: 6'''';
        Screen technology: E-Ink Carta;
        Illumination: Yes;
        Touch screen: Yes'
           , 545, 0, 545, 4.7, 6);

#----smartphones
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (70, 'Samsung Galaxy A32 4GB/128GB (purple)',
        'Screen: 6.4'''' 1080x2400 pixel, AMOLED (Super AMOLED Plus);
        Processor: Mediatek Helio G80 2 HHz;
        Memory: RAM 4 GB , 128 GB;
        SIM card format: Nano;
        Camera: 64 Mpx;
        Battery capacity: 5000 mA*h'
           , 943, 10, 849, 4.3, 7);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (71, 'Samsung Galaxy A32 4GB/128GB (purple)',
        'Screen: 6.51'''' 720x1600 pixel, IPS;
        Processor: MediaTek Helio P35 2.3 HHz;
        Memory: RAM 4 GB , 64 GB;
        SIM card format: Nano;
        Camera: 13 Mpx;
        Battery capacity: 5000 mA*h'
           , 399, 0, 399, 4.3, 7);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (72, 'Xiaomi Redmi 10 2022 4GB/128GB (carbon grey)',
        'Screen: 6.5'''' 1080x2400 pixel, IPS;
        Processor: Mediatek Helio G88 2 HHz;
        Memory: RAM 4 GB , 128 GB;
        SIM card format: Nano;
        Camera: 50 Mpx;
        Battery capacity: 5000 mA*h'
           , 679, 0, 679, 4.3, 7);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (73, 'Xiaomi Redmi Note 11 Pro 8GB/128GB Star Blue EU',
        'Screen: 6.67'''' 1080x2400 pixel, AMOLED;
        Processor: Mediatek Helio G96 2.05 HHz;
        Memory: RAM 8 GB , 128 GB;
        SIM card format: Nano;
        Camera: 108 Mpx;
        Battery capacity: 5000 mA*h'
           , 1166, 10, 1049, 4.7, 7);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (74, 'Samsung Galaxy A53 5G SM-A536EZKHSKZ 8GB/256GB (black)',
        'Screen: 6.5'''' 1080x2400 pixel, AMOLED (Super AMOLED);
        Processor: Exynos 1280 2.4 HHz;
        Memory: RAM 8 GB , 256 GB;
        SIM card format: Nano;
        Camera: 64 Mpx;
        Battery capacity: 5000 mA*h'
           , 1659, 0, 1659, 4.7, 7);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (75, 'Huawei nova Y70 Midnight Black (MGA-LX9N) 4GB/128GB',
        'Screen: 6.75'''' 720x1600 pixel, TFT;
        Processor: HiSilicon Kirin 710A 2 HHz;
        Memory: RAM 4 GB , 128 GB;
        SIM card format: Nano;
        Camera: 48 Mpx;
        Battery capacity: 6000 mA*h'
           , 704, 15, 599, 4.3, 7);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (76, 'Xiaomi Redmi Note 10 Pro 8GB/128GB Onyx Gray EU',
        'Screen: 6.67'''' 1080x2400 pixel, AMOLED;
        Processor: Qualcomm Snapdragon 732G 2.3 HHz;
        Memory: RAM 8 GB , 128 GB;
        SIM card format: Nano;
        Camera: 108 Mpx;
        Battery capacity: 5020 mA*h'
           , 1129, 0, 1129, 4.7, 7);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (77, 'HONOR X8 (TFY-LX1) 6GB/128GB (blue ocean)',
        'Screen: 6.7'''' 2388х1080 pixel, IPS;
        Processor: Qualcomm Snapdragon 680 2.4 HHz;
        Memory: RAM 6 GB , 128 GB;
        SIM card format: Nano;
        Camera: 64 Mpx;
        Battery capacity: 4000 mA*h'
           , 899, 0, 899, 4.3, 7);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (78, 'ZTE Blade V30 Vita NFC 4GB/128GB (grey)',
        'Screen: 6.82'''' 720x1520 pixel, IPS;
        Processor: UniSoC SC9863A 1.6 HHz;
        Memory: RAM 4 GB , 128 GB;
        SIM card format: Nano;
        Camera: 48 Mpx;
        Battery capacity: 5000 mA*h'
           , 634, 15, 539, 4.3, 7);
insert into eshop.products (id, name, description, price_before_discount, discount, price, rating, category_id)
values (79, 'Huawei Nova 9 SE JLN-LX1 8GB/128GB (crystal blue)',
        'Screen: 6.78'''' 2388х1080 pixel, IPS;
        Processor: Qualcomm Snapdragon 680 2.4 HHz;
        Memory: RAM 8 GB , 128 GB;
        SIM card format: Nano;
        Camera: 108 Mpx;
        Battery capacity: 4000 mA*h'
           , 999, 0, 999, 4.3, 7);

#--------------------------------------------------------
--  DML for Table eshop.images
# --------------------------------------------------------
#----categories
insert into eshop.images(category_id, primary_flag, image_path)
values (1, 1, 'laptops.jpg');
insert into eshop.images(category_id, primary_flag, image_path)
values (2, 1, 'monitors.jpg');
insert into eshop.images(category_id, primary_flag, image_path)
values (3, 1, 'pads.jpg');
insert into eshop.images(category_id, primary_flag, image_path)
values (4, 1, 'printers.jpg');
insert into eshop.images(category_id, primary_flag, image_path)
values (5, 1, 'skanners.jpg');
insert into eshop.images(category_id, primary_flag, image_path)
values (6, 1, 'ebooks.jpg');
insert into eshop.images(category_id, primary_flag, image_path)
values (7, 1, 'smartphones.jpg');
insert into eshop.images(category_id, primary_flag, image_path)
values (8, 1, 'smartwatches.jpg');
insert into eshop.images(category_id, primary_flag, image_path)
values (9, 1, 'headphones.jpg');
insert into eshop.images(category_id, primary_flag, image_path)
values (10, 1, 'washingmachines.jpg');
insert into eshop.images(category_id, primary_flag, image_path)
values (11, 1, 'irons.jpg');
insert into eshop.images(category_id, primary_flag, image_path)
values (12, 1, 'vacuumcleaners.jpg');
insert into eshop.images(category_id, primary_flag, image_path)
values (13, 1, 'fridges.jpg');
insert into eshop.images(category_id, primary_flag, image_path)
values (14, 1, 'cookers.jpg');
insert into eshop.images(category_id, primary_flag, image_path)
values (15, 1, 'kitchenhoods.jpg');
insert into eshop.images(category_id, primary_flag, image_path)
values (16, 1, 'dishwashers.jpg');
insert into eshop.images(category_id, primary_flag, image_path)
values (17, 1, 'tvs.jpg');
insert into eshop.images(category_id, primary_flag, image_path)
values (18, 1, 'sportssimulators.jpg');
insert into eshop.images(category_id, primary_flag, image_path)
values (19, 1, 'photocameras.jpg');
insert into eshop.images(category_id, primary_flag, image_path)
values (20, 1, 'videocameras.jpg');

#----laptops
insert into eshop.images(product_id, primary_flag, image_path)
values (1, 1, 'Honor MagicBook X14 NBR-WAI9 (5301AAPL)main.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (1, 0, 'Honor MagicBook X14 NBR-WAI9 (5301AAPL)1.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (1, 0, 'Honor MagicBook X14 NBR-WAI9 (5301AAPL)2.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (2, 1, 'Lenovo IdeaPad 3 15IGL05 81WQ0023REmain.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (2, 0, 'Lenovo IdeaPad 3 15IGL05 81WQ0023RE1.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (2, 0, 'Lenovo IdeaPad 3 15IGL05 81WQ0023RE2.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (3, 1, 'Haier U1520SDmain.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (3, 0, 'Haier U1520SD1.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (3, 0, 'Haier U1520SD2.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (4, 1, 'Asus VivoBook S14 S433EA-AM464main.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (4, 0, 'Asus VivoBook S14 S433EA-AM4641.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (4, 0, 'Asus VivoBook S14 S433EA-AM4642.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (5, 1, 'HP 15s-eq2115nw (4Y0U8EA)main.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (5, 0, 'HP 15s-eq2115nw (4Y0U8EA)1.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (5, 0, 'HP 15s-eq2115nw (4Y0U8EA)2.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (6, 1, 'HP Victus 16-e0154nw (4H3Z1EA)main.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (6, 0, 'HP Victus 16-e0154nw (4H3Z1EA)1.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (6, 0, 'HP Victus 16-e0154nw (4H3Z1EA)2.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (7, 1, 'Asus E210MA-GJ365main.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (7, 0, 'Asus E210MA-GJ3651.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (7, 0, 'Asus E210MA-GJ3652.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (8, 1, 'Lenovo IdeaPad 3 15IML05 81WB00QXREmain.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (8, 0, 'Lenovo IdeaPad 3 15IML05 81WB00QXRE1.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (8, 0, 'Lenovo IdeaPad 3 15IML05 81WB00QXRE2.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (9, 1, 'Lenovo V14 GEN2 ALC 82KC003NRUmain.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (9, 0, 'Lenovo V14 GEN2 ALC 82KC003NRU1.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (9, 0, 'Lenovo V14 GEN2 ALC 82KC003NRU2.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (10, 1, 'Lenovo IdeaPad 5 15ALC05 82LN00T5REmain.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (10, 0, 'Lenovo IdeaPad 5 15ALC05 82LN00T5RE1.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (10, 0, 'Lenovo IdeaPad 5 15ALC05 82LN00T5RE2.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (11, 1, 'Acer Aspire 3 A315-56-55RH (NX.HS5EU.029)main.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (11, 0, 'Acer Aspire 3 A315-56-55RH (NX.HS5EU.029)1.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (11, 0, 'Acer Aspire 3 A315-56-55RH (NX.HS5EU.029)2.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (12, 1, 'Acer Nitro 5 AN517-54-507Ymain.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (12, 0, 'Acer Nitro 5 AN517-54-507Y1.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (12, 0, 'Acer Nitro 5 AN517-54-507Y2.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (13, 1, 'Lenovo IdeaPad L3 15ITL6 82HL006KREmain.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (13, 0, 'Lenovo IdeaPad L3 15ITL6 82HL006KRE1.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (13, 0, 'Lenovo IdeaPad L3 15ITL6 82HL006KRE2.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (14, 1, 'Acer Swift 1 SF114-34-P9YY (NX.A77EU.00Y)main.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (14, 0, 'Acer Swift 1 SF114-34-P9YY (NX.A77EU.00Y)1.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (14, 0, 'Acer Swift 1 SF114-34-P9YY (NX.A77EU.00Y)2.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (15, 1, 'Asus E410MA-BV1517main.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (15, 0, 'Asus E410MA-BV15171.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (15, 0, 'Asus E410MA-BV15172.jpg');

#----monitors
insert into eshop.images(product_id, primary_flag, image_path)
values (16, 1, 'LG 24MK600M-Bmain.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (16, 0, 'LG 24MK600M-B1.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (16, 0, 'LG 24MK600M-B2.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (17, 1, 'Gigabyte Aorus FI27Qmain.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (17, 0, 'Gigabyte Aorus FI27Q1.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (17, 0, 'Gigabyte Aorus FI27Q2.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (18, 1, 'LG 27MK600M-Bmain.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (18, 0, 'LG 27MK600M-B1.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (18, 0, 'LG 27MK600M-B2.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (19, 1, 'Samsung S24R350FZI (LS24R350FZIXCI)main.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (19, 0, 'Samsung S24R350FZI (LS24R350FZIXCI)1.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (19, 0, 'Samsung S24R350FZI (LS24R350FZIXCI)2.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (20, 1, 'good_img_8498b90d-77a6-11e7-80eb-005056842056_600main.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (20, 0, 'good_img_8498b90d-77a6-11e7-80eb-005056842056_6001.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (20, 0, 'good_img_8498b90d-77a6-11e7-80eb-005056842056_6002.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (21, 1, 'c27g2zu-bk-monitor-aoc-1_200main.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (21, 0, 'c27g2zu-bk-monitor-aoc-1_2001.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (21, 0, 'c27g2zu-bk-monitor-aoc-1_2002.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (22, 1, 'LG UltraWide 34WP500-Bmain.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (22, 0, 'LG UltraWide 34WP500-B1.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (22, 0, 'LG UltraWide 34WP500-B2.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (23, 1, 'ASUS VP229HEmain.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (23, 0, 'ASUS VP229HE1.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (23, 0, 'ASUS VP229HE2.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (24, 1, '241v8l-01-monitor-philips-1_600.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (24, 0, '241v8l-01-monitor-philips-1_6001.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (24, 0, '241v8l-01-monitor-philips-1_6002.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (25, 1, 'AOC 24B2XHM2main.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (25, 0, 'AOC 24B2XHM21.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (25, 0, 'AOC 24B2XHM22.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (26, 1, 'ViewSonic VA3456-MHDJmain.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (26, 0, 'ViewSonic VA3456-MHDJ1.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (26, 0, 'ViewSonic VA3456-MHDJ2.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (27, 1, 'HP V27i (9SV94AA)main.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (27, 0, 'HP V27i (9SV94AA)1.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (27, 0, 'HP V27i (9SV94AA)2.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (28, 1, 'Lenovo G27q-20 (66C3GAC1EU)main.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (28, 0, 'Lenovo G27q-20 (66C3GAC1EU)1.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (28, 0, 'Lenovo G27q-20 (66C3GAC1EU)2.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (29, 1, 'ASUS TUF Gaming VG259QMmain.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (29, 0, 'ASUS TUF Gaming VG259QM1.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (29, 0, 'ASUS TUF Gaming VG259QM2.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (30, 1, 'Samsung F27T352FHImain.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (30, 0, 'Samsung F27T352FHI1.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (30, 0, 'Samsung F27T352FHI2.jpg');

#----pads
insert into eshop.images(product_id, primary_flag, image_path)
values (31, 1, 'Lenovo Tab M10 FHD Plus TB-X606X 64GB LTE ZA5V0083UA (grey)main.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (31, 0, 'Lenovo Tab M10 FHD Plus TB-X606X 64GB LTE ZA5V0083UA (grey)1.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (31, 0, 'Lenovo Tab M10 FHD Plus TB-X606X 64GB LTE ZA5V0083UA (grey)2.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (32, 1, 'Samsung Galaxy Tab A8 LTE SM-X205 32GB (SM-X205NZAACAU) (grey)main.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (32, 0, 'Samsung Galaxy Tab A8 LTE SM-X205 32GB (SM-X205NZAACAU) (grey)1.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (32, 0, 'Samsung Galaxy Tab A8 LTE SM-X205 32GB (SM-X205NZAACAU) (grey)2.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (33, 1, 'tb-8505x-za5h0073ua-planshet-lenovo-1_600.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (33, 0, 'tb-8505x-za5h0073ua-planshet-lenovo-1_6001.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (33, 0, 'tb-8505x-za5h0073ua-planshet-lenovo-1_6002.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (34, 1, 'matepad-lte-bah4-l09-4gb-64gb-planshet-huawei-1_600.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (34, 0, 'matepad-lte-bah4-l09-4gb-64gb-planshet-huawei-1_6001.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (34, 0, 'matepad-lte-bah4-l09-4gb-64gb-planshet-huawei-1_6002.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (35, 1, 'za7d0074ua-portativnyy-planshetnyy-kompyuter-tb-j706l-tab-6g-plus128gsg-ua-kb-pluspen-lenovo-1_600.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (35, 0, 'za7d0074ua-portativnyy-planshetnyy-kompyuter-tb-j706l-tab-6g-plus128gsg-ua-kb-pluspen-lenovo-1_6001.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (35, 0, 'za7d0074ua-portativnyy-planshetnyy-kompyuter-tb-j706l-tab-6g-plus128gsg-ua-kb-pluspen-lenovo-1_6002.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (36, 1, '10-tabmax-4g-9295g-4gb-64gb-frost-blue-planshet-tcl-1_600.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (36, 0, '10-tabmax-4g-9295g-4gb-64gb-frost-blue-planshet-tcl-1_6001.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (36, 0, '10-tabmax-4g-9295g-4gb-64gb-frost-blue-planshet-tcl-1_6002.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (37, 1, '10s-9081x-4gb-64gb-ethereal-sky-planshet-tcl-1_600.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (37, 0, '10s-9081x-4gb-64gb-ethereal-sky-planshet-tcl-1_6001.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (37, 0, '10s-9081x-4gb-64gb-ethereal-sky-planshet-tcl-1_6002.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (38, 1, 'pmt3197-w-d-pk-planshet-prestigio-1_600.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (38, 0, 'pmt3197-w-d-pk-planshet-prestigio-1_6001.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (38, 0, 'pmt3197-w-d-pk-planshet-prestigio-1_6002.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (39, 1, 'pmt4208-3g-e-ru-planshet-prestigio-1_600.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (39, 0, 'pmt4208-3g-e-ru-planshet-prestigio-1_6001.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (39, 0, 'pmt4208-3g-e-ru-planshet-prestigio-1_6002.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (40, 1, 'x8-black-planshet-vertex-1_600.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (40, 0, 'x8-black-planshet-vertex-1_6001.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (40, 0, 'x8-black-planshet-vertex-1_6002.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (41, 1, '9296g-tabmax-4gb-64gb-space-gray-planshet-tcl-1_600.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (41, 0, '9296g-tabmax-4gb-64gb-space-gray-planshet-tcl-1_6001.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (41, 0, '9296g-tabmax-4gb-64gb-space-gray-planshet-tcl-1_6002.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (42, 1, '10s-4g-9080g-3gb-32gb-gray-planshet-tcl-1_600.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (42, 0, '10s-4g-9080g-3gb-32gb-gray-planshet-tcl-1_6001.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (42, 0, '10s-4g-9080g-3gb-32gb-gray-planshet-tcl-1_6002.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (43, 1, 'good_img_87d6e620-d117-11e8-80c4-005056840c70_600.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (43, 0, 'good_img_87d6e620-d117-11e8-80c4-005056840c70_6001.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (43, 0, 'good_img_87d6e620-d117-11e8-80c4-005056840c70_6001.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (44, 1, 'bq-7038g-light-plus-black-planshet-bq-1_600.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (44, 0, 'bq-7038g-light-plus-black-planshet-bq-1_6001.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (44, 0, 'bq-7038g-light-plus-black-planshet-bq-1_6002.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (45, 1, 'good_img_48a5c57c-b750-11e9-80c7-005056840c70_600.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (45, 0, 'good_img_48a5c57c-b750-11e9-80c7-005056840c70_6001.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (45, 0, 'good_img_48a5c57c-b750-11e9-80c7-005056840c70_6002.jpg');

# #----printers
insert into eshop.images(product_id, primary_flag, image_path)
values (46, 1, 'Canon PIXMA TS304main.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (46, 0, 'Canon PIXMA TS3041.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (46, 0, 'Canon PIXMA TS3042.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (47, 1, 'Pantum P2200main.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (47, 0, 'Pantum P22001.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (47, 0, 'Pantum P22002.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (48, 1, 'Canon PIXMA G540 (4621C009)main.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (48, 0, 'Canon PIXMA G540 (4621C009)1.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (48, 0, 'Canon PIXMA G540 (4621C009)2.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (49, 1, 'HP Color Laser Printer 150nw (4ZB95A)main.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (49, 0, 'HP Color Laser Printer 150nw (4ZB95A)1.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (49, 0, 'HP Color Laser Printer 150nw (4ZB95A)2.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (50, 1, 'PANTUM P2507main.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (50, 0, 'PANTUM P25071.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (50, 0, 'PANTUM P25072.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (51, 1, 'HP Laser 107a (4ZB77A)main.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (51, 0, 'HP Laser 107a (4ZB77A)1.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (51, 0, 'HP Laser 107a (4ZB77A)2.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (52, 1, 'CANON I-SENSYS LBP352Xmain.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (52, 0, 'CANON I-SENSYS LBP352X1.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (52, 0, 'CANON I-SENSYS LBP352X2.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (53, 1, 'HP Laser 107w (4ZB78A)main.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (53, 0, 'HP Laser 107w (4ZB78A)1.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (53, 0, 'HP Laser 107w (4ZB78A)2.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (54, 1, 'Pantum P2516main.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (54, 0, 'Pantum P25161.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (54, 0, 'Pantum P25162.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (55, 1, 'HP LaserJet Pro M404n (W1A52A)main.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (55, 0, 'HP LaserJet Pro M404n (W1A52A)1.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (55, 0, 'HP LaserJet Pro M404n (W1A52A)2.jpg');

#----scanners
insert into eshop.images(product_id, primary_flag, image_path)
values (56, 1, 'CANON imageFORMULA P-215IImain.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (56, 0, 'CANON imageFORMULA P-215II1.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (56, 0, 'CANON imageFORMULA P-215II2.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (57, 1, 'Canon imageFORMULA DR-S130 (4812C001)main.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (57, 0, 'Canon imageFORMULA DR-S130 (4812C001)1.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (57, 0, 'Canon imageFORMULA DR-S130 (4812C001)2.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (58, 1, 'Canon imageFORMULA DR-C225W IImain.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (58, 0, 'Canon imageFORMULA DR-C225W II1.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (58, 0, 'Canon imageFORMULA DR-C225W II2.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (59, 1, 'CANON imageFORMULA DR-C230main.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (59, 0, 'CANON imageFORMULA DR-C2301.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (59, 0, 'CANON imageFORMULA DR-C2302.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (60, 1, 'CANON DR-M160IImain.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (60, 0, 'CANON DR-M160II1.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (60, 0, 'CANON DR-M160II2.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (61, 1, 'CANON DR-C240main.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (61, 0, 'CANON DR-C2401.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (61, 0, 'CANON DR-C2402.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (62, 1, 'CANON imageFORMULA DR-M260main.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (62, 0, 'CANON imageFORMULA DR-M2601.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (62, 0, 'CANON imageFORMULA DR-M2602.jpg');

# #----ebooks
insert into eshop.images(product_id, primary_flag, image_path)
values (63, 1, 'PocketBook 617 Black (PB617-P-CIS)main.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (63, 0, 'PocketBook 617 Black (PB617-P-CIS)1.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (63, 0, 'PocketBook 617 Black (PB617-P-CIS)2.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (64, 1, 'Amazon Kindle Touch 2019 8GB (black)main.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (64, 0, 'Amazon Kindle Touch 2019 8GB (black)1.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (64, 0, 'Amazon Kindle Touch 2019 8GB (black)2.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (65, 1, 'PocketBook 616 (silver)main.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (65, 0, 'PocketBook 616 (silver)1.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (65, 0, 'PocketBook 616 (silver)2.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (66, 1, 'Amazon Kindle Touch 2019 8GB (white)main.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (66, 0, 'Amazon Kindle Touch 2019 8GB (white)1.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (66, 0, 'Amazon Kindle Touch 2019 8GB (white)2.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (67, 1, 'Ritmix RBK-617 (black)main.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (67, 0, 'Ritmix RBK-617 (black)1.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (67, 0, 'Ritmix RBK-617 (black)2.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (68, 1, 'Amazon Kindle Paperwhite 8GB (sage)main.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (68, 0, 'Amazon Kindle Paperwhite 8GB (sage)1.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (68, 0, 'Amazon Kindle Paperwhite 8GB (sage)2.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (69, 1, 'PocketBook 632 Copper (PB632-K-CIS)main.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (69, 0, 'PocketBook 632 Copper (PB632-K-CIS)1.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (69, 0, 'PocketBook 632 Copper (PB632-K-CIS)2.jpg');

# #----smartphones
insert into eshop.images(product_id, primary_flag, image_path)
values (70, 1, 'sm-a325flvgcau-fiolet-128gb-telefon-gsm-samsung-galaxy-a32-1_600.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (70, 0, 'sm-a325flvgcau-fiolet-128gb-telefon-gsm-samsung-galaxy-a32-1_6001.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (70, 0, 'sm-a325flvgcau-fiolet-128gb-telefon-gsm-samsung-galaxy-a32-1_6002.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (71, 1, 'y21-4gb-64gb-diamond-glow-telefon-gsm-vivo-1_600.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (71, 0, 'y21-4gb-64gb-diamond-glow-telefon-gsm-vivo-1_6001.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (71, 0, 'y21-4gb-64gb-diamond-glow-telefon-gsm-vivo-1_6002.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (72, 1, 'redmi-10-2022-4gb-128gb-carbon-gray-eu-telefon-gsm-xiaomi-1_600.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (72, 0, 'redmi-10-2022-4gb-128gb-carbon-gray-eu-telefon-gsm-xiaomi-1_6001.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (72, 0, 'redmi-10-2022-4gb-128gb-carbon-gray-eu-telefon-gsm-xiaomi-1_6002.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (73, 1, 'redmi-note-11-pro-8gb-128gb-star-blue-eu-telefon-gsm-xiaomi-1_600.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (73, 0, 'redmi-note-11-pro-8gb-128gb-star-blue-eu-telefon-gsm-xiaomi-1_6001.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (73, 0, 'redmi-note-11-pro-8gb-128gb-star-blue-eu-telefon-gsm-xiaomi-1_6002.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (74, 1, 'sm-a536ezkhskz-chernyy-256gb-telefon-gsm-samsung-galaxy-a53-5g-1_600.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (74, 0, 'sm-a536ezkhskz-chernyy-256gb-telefon-gsm-samsung-galaxy-a53-5g-1_6001.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (75, 1, 'nova-y70-mga-lx9n-midnight-black-telefon-gsm-huawei-1_600.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (75, 0, 'nova-y70-mga-lx9n-midnight-black-telefon-gsm-huawei-1_6001.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (75, 0, 'nova-y70-mga-lx9n-midnight-black-telefon-gsm-huawei-1_6002.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (76, 1, 'redmi-note-10-pro-8gb-128gb-onyx-gray-eu-telefon-gsm-xiaomi-1_600.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (76, 0, 'redmi-note-10-pro-8gb-128gb-onyx-gray-eu-telefon-gsm-xiaomi-1_6001.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (76, 0, 'redmi-note-10-pro-8gb-128gb-onyx-gray-eu-telefon-gsm-xiaomi-1_6002.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (77, 1, 'x8-tfy-lx1-6gb-128gb-ocean-blue-telefon-gsm-honor-1_600.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (77, 0, 'x8-tfy-lx1-6gb-128gb-ocean-blue-telefon-gsm-honor-1_6001.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (77, 0, 'x8-tfy-lx1-6gb-128gb-ocean-blue-telefon-gsm-honor-1_6002.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (78, 1, 'blade-v30-vita-nfc-4gb-128gb-seryy-telefon-gsm-zte-1_600.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (78, 0, 'blade-v30-vita-nfc-4gb-128gb-seryy-telefon-gsm-zte-1_6001.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (78, 0, 'blade-v30-vita-nfc-4gb-128gb-seryy-telefon-gsm-zte-1_6002.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (79, 1, 'nova-9-se-jln-lx1-crystal-blue-telefon-gsm-huawei-1_600.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (79, 0, 'nova-9-se-jln-lx1-crystal-blue-telefon-gsm-huawei-1_6001.jpg');
insert into eshop.images(product_id, primary_flag, image_path)
values (79, 0, 'nova-9-se-jln-lx1-crystal-blue-telefon-gsm-huawei-1_6002.jpg');

#--------------------------------------------------------
#-- --  DML for Table eshop.roles
#-- --------------------------------------------------------
insert into eshop.roles (name)
values ('ROLE_ADMIN');
insert into eshop.roles (name)
values ('ROLE_USER');
insert into eshop.roles (name)
values ('ROLE_ANONIMUS');

#--------------------------------------------------------
#-- --  DML for Table eshop.USERS
#-- --------------------------------------------------------
insert into eshop.users (name, surname, email, password, login, birth_date, balance, role_id)
values ('admin', 'admin', 'admin@gmail.com', '$2a$10$/FQax/HRPNqcqwIJCWo2b.D4JSd6FKWq2e8FKpoNNCa9udRBpfWIy', 'admin',
        '1994-11-06', '100.00', 1);
insert into eshop.users (name, surname, email, password, login, birth_date, balance, role_id)
values ('denis', 'andreev', 'denis@gmail.com', '$2a$10$YLJCWM0Cz.TdXi1P5DjX3OUZHWtIvIVziBC55Ntp9QmJxnIybGsuS', 'vcxz',
        '1994-11-06', '80.00', 2);

