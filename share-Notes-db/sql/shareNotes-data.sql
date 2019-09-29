
-- 插入菜单表

INSERT INTO `menus` (`id`, `create_time`, `update_time`, `name`, `target`) VALUES
(1, '2019-06-21 03:21:19',  '2019-06-23 11:50:52', '公共',  '_self'),
(2, '2019-06-21 03:21:19',  '2019-06-23 11:53:39', '私人',  '_self');


INSERT INTO `posts` (`type`, `id`, `create_time`, `update_time`, `create_from`, `disallow_comment`, `edit_time`, `format_content`, `original_content`, `title`, `top_priority`, `visits`) VALUES
(0, 1, '2019-06-15 03:21:19',  '2019-06-15 04:22:40', 3, 0, '2019-06-15 04:22:40', '<h1 id="its-a-new-blog">It''s a new blog</h1>\n<ul>\n<li>Is me friends</li>\n<li>how are you</li>\n<li>I hava a sun</li>\n<li>but iss missing</li>\n<li>I need money to pay it back</li>\n<li>It will be thankful for you give me 100</li>\n</ul>\n',  '# It''s a new blog\r\n- I have a son。\r\n- Is named me。', 'Hello', 1, 51),
(0, 2, '2019-06-15 03:21:19', '2019-06-15 04:21:40', 3, 0, '2019-06-15 04:22:40', '<h1 id="its-a-old-blog">It''s a new blog</h1>\n<ul>\n<li>Is me</li>\n<li>how are you</li>\n<li>I hava a sun</li>\n<li>but iss missing</li>\n<li>I need money to pay it back</li>\n<li>It will be thankful for you give me 100</li>\n</ul>\n',  '# It''s a new blog\r\n- I have a son。\r\n- Is named me。', 'H',  1, 51),
(0, 3, '2019-06-15 03:21:19',  '2019-06-15 04:23:40', 3, 0, '2019-06-15 04:22:40', '<h1 id="its-a-new-blog">It''s a new blog</h1>\n<ul>\n<li>Is me friends</li>\n<li>how are you</li>\n<li>I hava a sun</li>\n<li>but iss missing</li>\n<li>I need money to pay it back</li>\n<li>It will be thankful for you give me 100</li>\n</ul>\n',  '# It''s a new blog\r\n- I have a son。\r\n- Is named me。', 'llo', 1, 51),
(0, 4, '2019-06-15 03:21:19', '2019-06-15 04:24:40', 3, 0, '2019-06-15 04:22:40', '<h1 id="its-a-new-blog">It''s a new blog</h1>\n<ul>\n<li>Is me friends</li>\n<li>how are you</li>\n<li>I hava a sun</li>\n<li>but iss missing</li>\n<li>I need money to pay it back</li>\n<li>It will be thankful for you give me 100</li>\n</ul>\n',  '# It''s a new blog\r\n- I have a son。\r\n- Is named me。', 'elo',  1, 51),
(0, 5, '2019-06-15 03:21:19', '2019-06-15 04:25:40', 3, 0, '2019-06-15 04:22:40', '<h1 id="its-a-new-blog">It''s a new blog</h1>\n<ul>\n<li>Is me friends</li>\n<li>how are you</li>\n<li>I hava a sun</li>\n<li>but iss missing</li>\n<li>I need money to pay it back</li>\n<li>It will be thankful for you give me 100</li>\n</ul>\n', '# It''s a new blog\r\n- I have a son。\r\n- Is named me。', 'ello',  1, 51);


INSERT INTO `post_categories` (`id`, `create_time`, `update_time`, `category_id`, `post_id`) VALUES
(5, '2019-06-26 14:49:06',  '2019-06-26 14:49:06', 1, 1),
(1, '2019-06-26 14:49:06',  '2019-06-26 14:49:06', 2, 2),
(2, '2019-06-26 15:25:53',  '2019-06-26 15:25:53', 3, 3),
(3, '2019-06-26 15:36:46',  '2019-06-26 15:36:46', 4, 4),
(4, '2019-06-28 02:56:18', '2019-06-28 02:56:18', 4, 5);

INSERT INTO `categories` (`id`, `create_time`, `update_time`, `description`, `name`, `parent_id`, `slug_name`) VALUES
(1, '2019-06-23 11:43:07',  '2019-06-23 11:43:07', '', '框架工具随记', 1, 'utils marks'),
(2, '2019-06-29 07:38:52',  '2019-06-29 07:38:52', '', 'SpringBoot', 1, 'springboot'),
(3, '2019-07-20 13:54:20', '2019-07-20 13:54:20', '', '喷人语录', 2, 'JavaBase'),
(4, '2019-08-03 09:18:01', '2019-08-03 09:18:01', '', '笔记', 1, '1564823881341');