
select pl.Play_ID, us.User_ID, us.User_Name, pl.Play_FirstName, pl.Play_LastName, tp.Type_Name, tp.Type_StartGebuehr, tpl.IsInNuLiga
from typeperplayer tpl
inner join player pl on tpl.typl_play_id = pl.Play_ID
inner join type tp on tpl.typl_type_id = tp.Type_ID
inner join user us on pl.Play_User_ID = us.User_ID
where tpl.IsInNuLiga = 10;