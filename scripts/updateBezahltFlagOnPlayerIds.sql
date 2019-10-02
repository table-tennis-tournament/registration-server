#Bitte das In-Statement bearbeiten. Hier alle Spieler-Ids eingeben, die den Status erhalten sollen.
SET @bezahltFlag=100; #bezahlt flag 1 vorbezahlt, 2 ... 100 deleted

update typeperplayer tpl 

inner join player pl on tpl.typl_play_id = pl.Play_ID
inner join type tp on tpl.typl_type_id = tp.Type_ID
inner join user us on pl.Play_User_ID = us.User_ID
set tpl.typl_paid = @bezahltFlag
where pl.Play_ID IN (1 , 2, 3 )