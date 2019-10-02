SET @disciplineId=28; #id der Konkurrenz, von der alle Spieler auf Exportiert gestellt werden sollen

update typeperplayer tpl 
inner join player pl on tpl.typl_play_id = pl.Play_ID
inner join type tp on tpl.typl_type_id = tp.Type_ID
inner join user us on pl.Play_User_ID = us.User_ID
set tpl.IsInNuLiga = 10
where tpl.typl_type_id = @disciplineId;