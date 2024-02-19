create database if not exists `rvi_analyzer`;

use `rvi_analyzer`;


INSERT INTO user_role (role_id, role_name) VALUES
('create_parameter', 'CREATE_PARAMETER'),
('create_rm_tracking', 'CREATE_RM_TRACKING'),
('create_test_result', 'CREATE_TEST_RESULT'),
('create_rm', 'CREATE_RM'),
('create_customer_po', 'CREATE_CUSTOMER_PO'),
('create_so_number', 'CREATE_SO_NUMBER'),
('create_production_order', 'CREATE_PRODUCTION_ORDER'),
('get_all_parameter', 'GET_ALL_PARAMETER'),
('get_all_test_result', 'GET_ALL_TEST_RESULT'),
('get_all_rm', 'GET_ALL_RM'),
('get_all_rm_tracking', 'GET_ALL_RM_TRACKING'),
('get_all_customer_po', 'GET_ALL_CUSTOMER_PO'),
('get_all_so_number', 'GET_ALL_SO_NUMBER'),
('get_all_production_order', 'GET_ALL_PRODUCTION_ORDER'),
('create_test', 'CREATE_TEST'),
('get_all_tests', 'GET_ALL_TESTS'),
('get_tests', 'GET_TESTS'),
('create_style', 'CREATE_STYLE'),
('get_all_styles', 'GET_ALL_STYLES'),
('create_plant', 'CREATE_PLANT'),
('get_plants', 'GET_PLANTS'),
('get_all_plants', 'GET_ALL_PLANTS'),
('create_material', 'CREATE_MATERIAL'),
('get_all_materials', 'GET_ALL_MATERIALS'),
('allocate_customer', 'ALLOCATE_CUSTOMER'),
('update_style', 'UPDATE_STYLE'),
('allocate_admin', 'ALLOCATE_ADMIN'),
('login_web', 'LOGIN_WEB'),
('login_app', 'LOGIN_APP'),
('create_top_admin', 'CREATE_TOP_ADMIN'),
('create_admin', 'CREATE_ADMIN'),
('create_user', 'CREATE_USER'),
('create_customer', 'CREATE_CUSTOMER'),
('reset_password', 'RESET_PASSWORD'),
('update_device', 'UPDATE_DEVICE'),
('save_mode_one', 'SAVE_MODE_ONE'),
('save_mode_two', 'SAVE_MODE_TWO'),
('save_mode_three', 'SAVE_MODE_THREE'),
('save_mode_four', 'SAVE_MODE_FOUR'),
('save_mode_five', 'SAVE_MODE_FIVE'),
('save_mode_six', 'SAVE_MODE_SIX'),
('get_mode_one', 'GET_MODE_ONE'),
('get_mode_two', 'GET_MODE_TWO'),
('get_mode_three', 'GET_MODE_THREE'),
('get_mode_four', 'GET_MODE_FOUR'),
('get_mode_five', 'GET_MODE_FIVE'),
('get_mode_six', 'GET_MODE_SIX'),
('share_report', 'SHARE_REPORT'),
('get_users', 'GET_USERS'),
('get_all_customers', 'GET_ALL_CUSTOMERS'),
('update_user', 'UPDATE_USER'),
('update_admin_user', 'UPDATE_ADMIN_USER'),
('update_customer', 'UPDATE_CUSTOMER'),
('get_all_users', 'GET_ALL_USERS'),
('get_devices', 'GET_DEVICES'),
('get_last_mode_one', 'GET_LAST_MODE_ONE'),
('get_last_mode_two', 'GET_LAST_MODE_TWO'),
('get_last_mode_three', 'GET_LAST_MODE_THREE'),
('get_last_mode_four', 'GET_LAST_MODE_FOUR'),
('get_last_mode_five', 'GET_LAST_MODE_FIVE'),
('get_last_mode_six', 'GET_LAST_MODE_SIX');

INSERT INTO user_group (group_id, group_name) VALUES
('top_admin_group', 'TOP_ADMIN'),
('admin_group', 'ADMIN'),
('user_group', 'USER');

-- Insert data for top_admin_group
INSERT INTO group_role (group_id, role_id) VALUES
('top_admin_group', 'login_web'),
('top_admin_group', 'create_top_admin'),
('top_admin_group', 'create_user'),
('top_admin_group', 'create_customer'),
('top_admin_group', 'create_plant'),
('top_admin_group', 'create_material'),
('top_admin_group', 'create_style'),
('top_admin_group', 'create_test'),
('top_admin_group', 'update_admin_user'),
('top_admin_group', 'get_all_plants'),
('top_admin_group', 'get_all_users'),
('top_admin_group', 'get_all_customers'),
('top_admin_group', 'get_all_tests'),
('top_admin_group', 'get_all_styles'),
('top_admin_group', 'get_all_materials'),
('top_admin_group', 'create_admin'),
('top_admin_group', 'update_customer'),
('top_admin_group', 'update_style'),
('top_admin_group', 'reset_password'),
('top_admin_group', 'update_device'),
('top_admin_group', 'allocate_admin'),
('top_admin_group', 'allocate_customer'),
('top_admin_group', 'get_devices');

-- Insert data for admin_group
INSERT INTO group_role (group_id, role_id) VALUES
('admin_group', 'login_web'),
('admin_group', 'login_app'),
('admin_group', 'create_material'),
('admin_group', 'create_parameter'),
('admin_group', 'create_test_result'),
('admin_group', 'create_rm_tracking'),
('admin_group', 'create_rm'),
('admin_group', 'create_customer_po'),
('admin_group', 'create_so_number'),
('admin_group', 'create_production_order'),
('admin_group', 'create_user'),
('admin_group', 'create_test'),
('admin_group', 'reset_password'),
('admin_group', 'get_all_parameter'),
('admin_group', 'get_all_test_result'),
('admin_group', 'get_all_rm_tracking'),
('admin_group', 'get_all_rm'),
('admin_group', 'get_all_customer_po'),
('admin_group', 'get_all_so_number'),
('admin_group', 'get_all_production_order'),
('admin_group', 'get_tests'),
('admin_group', 'get_all_materials'),
('admin_group', 'get_mode_one'),
('admin_group', 'get_mode_two'),
('admin_group', 'get_mode_three'),
('admin_group', 'get_mode_four'),
('admin_group', 'get_mode_five'),
('admin_group', 'get_mode_six'),
('admin_group', 'share_report'),
('admin_group', 'get_users'),
('admin_group', 'update_user'),
('admin_group', 'get_devices');

-- Insert data for user_group
INSERT INTO group_role (group_id, role_id) VALUES
('user_group', 'login_app'),
-- ('user_group', 's_mode_one'),
-- ('user_group', 's_mode_two'),
-- ('user_group', 's_mode_three'),
-- ('user_group', 's_mode_four'),
-- ('user_group', 's_mode_five'),
-- ('user_group', 's_mode_six'),
('user_group', 'get_last_mode_one'),
('user_group', 'get_last_mode_two'),
('user_group', 'get_last_mode_three'),
('user_group', 'get_last_mode_four'),
('user_group', 'get_last_mode_five'),
('user_group', 'get_last_mode_six');

INSERT INTO `user` (
  `id`,
  `created-by`,
  `password`,
  `password-type`,
  `status`,
  `supervisor`,
  `username`,
  `group_id`
) VALUES (
  '1', -- Replace with the actual ID value
  'rukshan@gmail.com', -- Replace with the creator's username or ID
  'JP4Zq/pY4HDquTPjPLCSNzSnsyWu/pLHvUGXqYpyEpU', -- Replace with the hashed password
  'PASSWORD', -- Replace with the password type
  'ACTIVE', -- Replace with the user's status
  'supervisor_id', -- Replace with the supervisor's username or ID
  'rukshan@gmail.com', -- Replace with the user's username
  'top_admin_group' -- Replace with the actual group ID
);




