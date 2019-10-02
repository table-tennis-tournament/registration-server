select dbl.Doub_ID ,tp.Type_Name, pl1.Play_FirstName, pl1.Play_LastName, cl1.Club_Name, pl2.Play_FirstName, pl2.Play_LastName, cl2.Club_Name
from doubles dbl
Inner join player pl1 on pl1.Play_ID = dbl.Doub_Play1_ID
Inner join player pl2 on pl2.Play_ID = dbl.Doub_Play2_ID
inner join club cl1 on pl1.Play_Club_ID = cl1.Club_ID
inner join club cl2 on pl2.Play_Club_ID = cl2.Club_ID
inner join type tp on tp.Type_ID = dbl.Doub_Type_ID