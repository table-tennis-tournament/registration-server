select pl.Play_ID, pl.Play_LastName, pl.Play_FirstName, us.User_Name, cl.Club_Name, Year(pl.Play_BirthDate) as Geburtsjahr, tpl.Timestamp as Meldedatum, tpl.IsInNuLiga, pl.Play_TTR,
MAX(CASE 
		WHEN ( tp.Type_ID ='1') AND (tpl.typl_paid =0) THEN 'x' 
		WHEN ( tp.Type_ID ='1') AND (tpl.typl_paid =1) THEN 'v'
		WHEN ( tp.Type_ID ='1') AND (tpl.typl_paid =2) THEN 'n'
		WHEN ( tp.Type_ID ='1') AND (tpl.typl_paid =3) THEN 'w'
		WHEN ( tp.Type_ID ='1') AND (tpl.typl_paid =100) THEN 'd' ELSE ''
	END) as 'Mädchen U18 B',
MAX(CASE 
		WHEN ( tp.Type_ID ='2') AND (tpl.typl_paid =0) THEN 'x' 
		WHEN ( tp.Type_ID ='2') AND (tpl.typl_paid =1) THEN 'v'
		WHEN ( tp.Type_ID ='2') AND (tpl.typl_paid =2) THEN 'n'
		WHEN ( tp.Type_ID ='2') AND (tpl.typl_paid =3) THEN 'w'
		WHEN ( tp.Type_ID ='2') AND (tpl.typl_paid =100) THEN 'd' ELSE ''
	END) as 'Jungen U18 C',
MAX(CASE 
		WHEN ( tp.Type_ID ='5') AND (tpl.typl_paid =0) THEN 'x' 
		WHEN ( tp.Type_ID ='5') AND (tpl.typl_paid =1) THEN 'v'
		WHEN ( tp.Type_ID ='5') AND (tpl.typl_paid =2) THEN 'n'
		WHEN ( tp.Type_ID ='5') AND (tpl.typl_paid =3) THEN 'w'
		WHEN ( tp.Type_ID ='5') AND (tpl.typl_paid =100) THEN 'd' ELSE ''
	END) as 'Jungen U18 B',
MAX(CASE 
		WHEN ( tp.Type_ID ='6') AND (tpl.typl_paid =0) THEN 'x' 
		WHEN ( tp.Type_ID ='6') AND (tpl.typl_paid =1) THEN 'v'
		WHEN ( tp.Type_ID ='6') AND (tpl.typl_paid =2) THEN 'n'
		WHEN ( tp.Type_ID ='6') AND (tpl.typl_paid =3) THEN 'w'
		WHEN ( tp.Type_ID ='6') AND (tpl.typl_paid =100) THEN 'd' ELSE ''
	END) as 'Mädchen U18 A',
MAX(CASE 
		WHEN ( tp.Type_ID ='7') AND (tpl.typl_paid =0) THEN 'x' 
		WHEN ( tp.Type_ID ='7') AND (tpl.typl_paid =1) THEN 'v'
		WHEN ( tp.Type_ID ='7') AND (tpl.typl_paid =2) THEN 'n'
		WHEN ( tp.Type_ID ='7') AND (tpl.typl_paid =3) THEN 'w'
		WHEN ( tp.Type_ID ='7') AND (tpl.typl_paid =100) THEN 'd' ELSE ''
	END) as 'Jungen U18 A',
MAX(CASE 
		WHEN ( tp.Type_ID ='9') AND (tpl.typl_paid =0) THEN 'x' 
		WHEN ( tp.Type_ID ='9') AND (tpl.typl_paid =1) THEN 'v'
		WHEN ( tp.Type_ID ='9') AND (tpl.typl_paid =2) THEN 'n'
		WHEN ( tp.Type_ID ='9') AND (tpl.typl_paid =3) THEN 'w'
		WHEN ( tp.Type_ID ='9') AND (tpl.typl_paid =100) THEN 'd' ELSE ''
	END) as 'Herren D',
MAX(CASE 
		WHEN ( tp.Type_ID ='11') AND (tpl.typl_paid =0) THEN 'x' 
		WHEN ( tp.Type_ID ='11') AND (tpl.typl_paid =1) THEN 'v'
		WHEN ( tp.Type_ID ='11') AND (tpl.typl_paid =2) THEN 'n'
		WHEN ( tp.Type_ID ='11') AND (tpl.typl_paid =3) THEN 'w'
		WHEN ( tp.Type_ID ='11') AND (tpl.typl_paid =100) THEN 'd' ELSE ''
	END) as 'Herren H',
MAX(CASE 
		WHEN ( tp.Type_ID ='12') AND (tpl.typl_paid =0) THEN 'x' 
		WHEN ( tp.Type_ID ='12') AND (tpl.typl_paid =1) THEN 'v'
		WHEN ( tp.Type_ID ='12') AND (tpl.typl_paid =2) THEN 'n'
		WHEN ( tp.Type_ID ='12') AND (tpl.typl_paid =3) THEN 'w'
		WHEN ( tp.Type_ID ='12') AND (tpl.typl_paid =100) THEN 'd' ELSE ''
	END) as 'Senioren A',
MAX(CASE 
		WHEN ( tp.Type_ID ='14') AND (tpl.typl_paid =0) THEN 'x' 
		WHEN ( tp.Type_ID ='14') AND (tpl.typl_paid =1) THEN 'v'
		WHEN ( tp.Type_ID ='14') AND (tpl.typl_paid =2) THEN 'n'
		WHEN ( tp.Type_ID ='14') AND (tpl.typl_paid =3) THEN 'w'
		WHEN ( tp.Type_ID ='14') AND (tpl.typl_paid =100) THEN 'd' ELSE ''
	END) as 'Senioren B',
MAX(CASE 
		WHEN ( tp.Type_ID ='15') AND (tpl.typl_paid =0) THEN 'x' 
		WHEN ( tp.Type_ID ='15') AND (tpl.typl_paid =1) THEN 'v'
		WHEN ( tp.Type_ID ='15') AND (tpl.typl_paid =2) THEN 'n'
		WHEN ( tp.Type_ID ='15') AND (tpl.typl_paid =3) THEN 'w'
		WHEN ( tp.Type_ID ='15') AND (tpl.typl_paid =100) THEN 'd' ELSE ''
	END) as 'Herren C',
MAX(CASE 
		WHEN ( tp.Type_ID ='17') AND (tpl.typl_paid =0) THEN 'x' 
		WHEN ( tp.Type_ID ='17') AND (tpl.typl_paid =1) THEN 'v'
		WHEN ( tp.Type_ID ='17') AND (tpl.typl_paid =2) THEN 'n'
		WHEN ( tp.Type_ID ='17') AND (tpl.typl_paid =3) THEN 'w'
		WHEN ( tp.Type_ID ='17') AND (tpl.typl_paid =100) THEN 'd' ELSE ''
	END) as 'Damen B',
MAX(CASE 
		WHEN ( tp.Type_ID ='18') AND (tpl.typl_paid =0) THEN 'x' 
		WHEN ( tp.Type_ID ='18') AND (tpl.typl_paid =1) THEN 'v'
		WHEN ( tp.Type_ID ='18') AND (tpl.typl_paid =2) THEN 'n'
		WHEN ( tp.Type_ID ='18') AND (tpl.typl_paid =3) THEN 'w'
		WHEN ( tp.Type_ID ='18') AND (tpl.typl_paid =100) THEN 'd' ELSE ''
	END) as 'Herren B',
MAX(CASE 
		WHEN ( tp.Type_ID ='20') AND (tpl.typl_paid =0) THEN 'x' 
		WHEN ( tp.Type_ID ='20') AND (tpl.typl_paid =1) THEN 'v'
		WHEN ( tp.Type_ID ='20') AND (tpl.typl_paid =2) THEN 'n'
		WHEN ( tp.Type_ID ='20') AND (tpl.typl_paid =3) THEN 'w'
		WHEN ( tp.Type_ID ='20') AND (tpl.typl_paid =100) THEN 'd' ELSE ''
	END) as 'Damen A',
MAX(CASE 
		WHEN ( tp.Type_ID ='22') AND (tpl.typl_paid =0) THEN 'x' 
		WHEN ( tp.Type_ID ='22') AND (tpl.typl_paid =1) THEN 'v'
		WHEN ( tp.Type_ID ='22') AND (tpl.typl_paid =2) THEN 'n'
		WHEN ( tp.Type_ID ='22') AND (tpl.typl_paid =3) THEN 'w'
		WHEN ( tp.Type_ID ='22') AND (tpl.typl_paid =100) THEN 'd' ELSE ''
	END) as 'Herren A'
from typeperplayer tpl
inner join player pl on pl.Play_ID = tpl.typl_play_id
inner join user us on us.User_ID = pl.Play_User_ID
inner join club cl on cl.Club_ID = pl.Play_Club_ID
left join type tp on tpl.typl_type_id=tp.Type_ID
group by pl.Play_ID
order by pl.Play_LastName