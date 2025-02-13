--
-- PostgreSQL database dump
--

-- Dumped from database version 16.3
-- Dumped by pg_dump version 16.3

-- Started on 2025-02-13 15:03:54

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 224 (class 1259 OID 19811)
-- Name: booking; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.booking (
    id bigint NOT NULL,
    user_id bigint NOT NULL,
    route_id bigint NOT NULL,
    departure_schedule_id bigint NOT NULL,
    arrival_schedule_id bigint NOT NULL,
    booking_date date NOT NULL,
    seat_number integer NOT NULL,
    price double precision NOT NULL,
    booking_time timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.booking OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 19810)
-- Name: booking_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.booking_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.booking_id_seq OWNER TO postgres;

--
-- TOC entry 4837 (class 0 OID 0)
-- Dependencies: 223
-- Name: booking_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.booking_id_seq OWNED BY public.booking.id;


--
-- TOC entry 216 (class 1259 OID 19768)
-- Name: bus_route; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.bus_route (
    id bigint NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE public.bus_route OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 19767)
-- Name: bus_route_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.bus_route_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.bus_route_id_seq OWNER TO postgres;

--
-- TOC entry 4838 (class 0 OID 0)
-- Dependencies: 215
-- Name: bus_route_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.bus_route_id_seq OWNED BY public.bus_route.id;


--
-- TOC entry 220 (class 1259 OID 19787)
-- Name: bus_schedule; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.bus_schedule (
    id bigint NOT NULL,
    stop_id bigint NOT NULL,
    arrival_time time without time zone NOT NULL,
    departure_time time without time zone
);


ALTER TABLE public.bus_schedule OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 19786)
-- Name: bus_schedule_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.bus_schedule_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.bus_schedule_id_seq OWNER TO postgres;

--
-- TOC entry 4839 (class 0 OID 0)
-- Dependencies: 219
-- Name: bus_schedule_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.bus_schedule_id_seq OWNED BY public.bus_schedule.id;


--
-- TOC entry 218 (class 1259 OID 19775)
-- Name: bus_stop; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.bus_stop (
    id bigint NOT NULL,
    route_id bigint NOT NULL,
    location character varying(255) NOT NULL,
    stop_order integer NOT NULL
);


ALTER TABLE public.bus_stop OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 19774)
-- Name: bus_stop_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.bus_stop_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.bus_stop_id_seq OWNER TO postgres;

--
-- TOC entry 4840 (class 0 OID 0)
-- Dependencies: 217
-- Name: bus_stop_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.bus_stop_id_seq OWNED BY public.bus_stop.id;


--
-- TOC entry 222 (class 1259 OID 19799)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id bigint NOT NULL,
    name character varying(255) NOT NULL,
    email character varying(255) NOT NULL,
    password_hash character varying(255) NOT NULL,
    credits double precision DEFAULT 0
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 19798)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_id_seq OWNER TO postgres;

--
-- TOC entry 4841 (class 0 OID 0)
-- Dependencies: 221
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- TOC entry 4659 (class 2604 OID 19838)
-- Name: booking id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.booking ALTER COLUMN id SET DEFAULT nextval('public.booking_id_seq'::regclass);


--
-- TOC entry 4654 (class 2604 OID 19885)
-- Name: bus_route id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bus_route ALTER COLUMN id SET DEFAULT nextval('public.bus_route_id_seq'::regclass);


--
-- TOC entry 4656 (class 2604 OID 19902)
-- Name: bus_schedule id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bus_schedule ALTER COLUMN id SET DEFAULT nextval('public.bus_schedule_id_seq'::regclass);


--
-- TOC entry 4655 (class 2604 OID 19928)
-- Name: bus_stop id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bus_stop ALTER COLUMN id SET DEFAULT nextval('public.bus_stop_id_seq'::regclass);


--
-- TOC entry 4657 (class 2604 OID 19950)
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- TOC entry 4831 (class 0 OID 19811)
-- Dependencies: 224
-- Data for Name: booking; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.booking (id, user_id, route_id, departure_schedule_id, arrival_schedule_id, booking_date, seat_number, price, booking_time) FROM stdin;
3	1	1	2	34	2025-02-16	1	2.5	2025-02-12 19:33:22.998607
5	1	1	10	34	2025-02-17	1	2	2025-02-13 01:11:53.194495
6	2	1	10	34	2025-02-20	1	2	2025-02-13 13:43:01.085636
\.


--
-- TOC entry 4823 (class 0 OID 19768)
-- Dependencies: 216
-- Data for Name: bus_route; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.bus_route (id, name) FROM stdin;
1	Route 402
\.


--
-- TOC entry 4827 (class 0 OID 19787)
-- Dependencies: 220
-- Data for Name: bus_schedule; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.bus_schedule (id, stop_id, arrival_time, departure_time) FROM stdin;
1	1	06:00:00	06:00:00
2	1	08:00:00	08:00:00
3	1	10:00:00	10:00:00
4	1	12:00:00	12:00:00
5	1	14:00:00	14:00:00
6	1	16:00:00	16:00:00
7	1	18:00:00	18:00:00
8	1	20:00:00	20:00:00
9	2	06:07:00	06:07:00
10	2	08:07:00	08:07:00
11	2	10:07:00	10:07:00
12	2	12:07:00	12:07:00
13	2	14:07:00	14:07:00
14	2	16:07:00	16:07:00
15	2	18:07:00	18:07:00
16	2	20:07:00	20:07:00
17	3	06:15:00	06:15:00
18	3	08:15:00	08:15:00
19	3	10:15:00	10:15:00
20	3	12:15:00	12:15:00
21	3	14:15:00	14:15:00
22	3	16:15:00	16:15:00
23	3	18:15:00	18:15:00
24	3	20:15:00	20:15:00
25	4	06:22:00	06:22:00
26	4	08:22:00	08:22:00
27	4	10:22:00	10:22:00
28	4	12:22:00	12:22:00
29	4	14:22:00	14:22:00
30	4	16:22:00	16:22:00
31	4	18:22:00	18:22:00
32	4	20:22:00	20:22:00
33	5	06:30:00	\N
34	5	08:30:00	\N
35	5	10:30:00	\N
36	5	12:30:00	\N
37	5	14:30:00	\N
38	5	16:30:00	\N
39	5	18:30:00	\N
40	5	20:30:00	\N
\.


--
-- TOC entry 4825 (class 0 OID 19775)
-- Dependencies: 218
-- Data for Name: bus_stop; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.bus_stop (id, route_id, location, stop_order) FROM stdin;
1	1	Knocknacarra	1
2	1	Rahoon	2
3	1	Westside	3
4	1	Newcastle	4
5	1	Galway City	5
\.


--
-- TOC entry 4829 (class 0 OID 19799)
-- Dependencies: 222
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, name, email, password_hash, credits) FROM stdin;
1	Diarmaid	d@gmail.com	$2a$10$jzILFjKtixLhM//9fytqKeZodFvtb9c8V5wMD5imBHj0E9lGKZ56C	100
2	Joe	joe@gmail.com	$2a$10$qhfCU4Sbw3s1ptuV7LFoiOMVF8VnSeD5oDahTdbFSOd89kt1n/GYS	100
\.


--
-- TOC entry 4842 (class 0 OID 0)
-- Dependencies: 223
-- Name: booking_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.booking_id_seq', 6, true);


--
-- TOC entry 4843 (class 0 OID 0)
-- Dependencies: 215
-- Name: bus_route_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.bus_route_id_seq', 1, true);


--
-- TOC entry 4844 (class 0 OID 0)
-- Dependencies: 219
-- Name: bus_schedule_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.bus_schedule_id_seq', 40, true);


--
-- TOC entry 4845 (class 0 OID 0)
-- Dependencies: 217
-- Name: bus_stop_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.bus_stop_id_seq', 5, true);


--
-- TOC entry 4846 (class 0 OID 0)
-- Dependencies: 221
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 2, true);


--
-- TOC entry 4672 (class 2606 OID 19840)
-- Name: booking booking_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.booking
    ADD CONSTRAINT booking_pkey PRIMARY KEY (id);


--
-- TOC entry 4662 (class 2606 OID 19887)
-- Name: bus_route bus_route_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bus_route
    ADD CONSTRAINT bus_route_pkey PRIMARY KEY (id);


--
-- TOC entry 4666 (class 2606 OID 19904)
-- Name: bus_schedule bus_schedule_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bus_schedule
    ADD CONSTRAINT bus_schedule_pkey PRIMARY KEY (id);


--
-- TOC entry 4664 (class 2606 OID 19930)
-- Name: bus_stop bus_stop_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bus_stop
    ADD CONSTRAINT bus_stop_pkey PRIMARY KEY (id);


--
-- TOC entry 4668 (class 2606 OID 19809)
-- Name: users users_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_email_key UNIQUE (email);


--
-- TOC entry 4670 (class 2606 OID 19952)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 4675 (class 2606 OID 19905)
-- Name: booking booking_arrival_schedule_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.booking
    ADD CONSTRAINT booking_arrival_schedule_id_fkey FOREIGN KEY (arrival_schedule_id) REFERENCES public.bus_schedule(id) ON DELETE CASCADE;


--
-- TOC entry 4676 (class 2606 OID 19910)
-- Name: booking booking_departure_schedule_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.booking
    ADD CONSTRAINT booking_departure_schedule_id_fkey FOREIGN KEY (departure_schedule_id) REFERENCES public.bus_schedule(id) ON DELETE CASCADE;


--
-- TOC entry 4677 (class 2606 OID 19893)
-- Name: booking booking_route_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.booking
    ADD CONSTRAINT booking_route_id_fkey FOREIGN KEY (route_id) REFERENCES public.bus_route(id) ON DELETE CASCADE;


--
-- TOC entry 4678 (class 2606 OID 19953)
-- Name: booking booking_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.booking
    ADD CONSTRAINT booking_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;


--
-- TOC entry 4674 (class 2606 OID 19931)
-- Name: bus_schedule bus_schedule_stop_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bus_schedule
    ADD CONSTRAINT bus_schedule_stop_id_fkey FOREIGN KEY (stop_id) REFERENCES public.bus_stop(id) ON DELETE CASCADE;


--
-- TOC entry 4673 (class 2606 OID 19940)
-- Name: bus_stop bus_stop_route_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bus_stop
    ADD CONSTRAINT bus_stop_route_id_fkey FOREIGN KEY (route_id) REFERENCES public.bus_route(id) ON DELETE CASCADE;


-- Completed on 2025-02-13 15:03:54

--
-- PostgreSQL database dump complete
--

