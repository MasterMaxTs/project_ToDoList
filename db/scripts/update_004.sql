UPDATE todo_tasks SET priority_id =
    (SELECT id FROM todo_priorities WHERE name = 'срочный' LIMIT 1);