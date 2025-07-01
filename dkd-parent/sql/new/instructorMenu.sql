-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('导员', '2009', '1', 'instructor', 'manage/instructor/index', 1, 0, 'C', '0', '0', 'manage:instructor:list', '#', 'admin', sysdate(), '', null, '导员菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('导员查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'manage:instructor:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('导员新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'manage:instructor:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('导员修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'manage:instructor:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('导员删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'manage:instructor:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('导员导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'manage:instructor:export',       '#', 'admin', sysdate(), '', null, '');