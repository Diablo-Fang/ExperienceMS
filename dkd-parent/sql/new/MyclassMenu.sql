-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('班级', '2009', '1', 'Myclass', 'manage/Myclass/index', 1, 0, 'C', '0', '0', 'manage:Myclass:list', '#', 'admin', sysdate(), '', null, '班级菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('班级查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'manage:Myclass:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('班级新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'manage:Myclass:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('班级修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'manage:Myclass:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('班级删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'manage:Myclass:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('班级导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'manage:Myclass:export',       '#', 'admin', sysdate(), '', null, '');