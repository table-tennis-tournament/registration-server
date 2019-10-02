SET @user_id=28; # id des nutzers (Rechnungsnummer)


select pl.Play_ID, us.User_ID, us.User_Name, pl.Play_FirstName, pl.Play_LastName, tp.Type_Name, tp.Type_StartGebuehr, tpl.typl_paid
from typeperplayer tpl
inner join player pl on tpl.typl_play_id = pl.Play_ID
inner join type tp on tpl.typl_type_id = tp.Type_ID
inner join user us on pl.Play_User_ID = us.User_ID
where us.User_ID = @user_id;