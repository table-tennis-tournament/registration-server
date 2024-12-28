insert into matches (Matc_Play1_ID, Matc_Play2_ID, Matc_IsPlaying, Matc_Winner_ID,
 Matc_Type_ID, Matc_Tabl_ID, Matc_Tabl2_ID, Matc_StartTime, Matc_RoundNumber, Matc_ResultRaw, Matc_MaTy_ID, 
 Matc_Played, Matc_Parent_ID, Matc_TeSM_ID, Matc_Grou_ID, Matc_Result, Matc_ResultTeam1, Matc_ResultTeam2, Matc_Code)
select p.Play_ID, 0, 0, p.Play_ID, 
tp.typl_type_id,0, 0, '1899-12-30 00:00:00', 0, '', 1, 
1, 0,0, 999, '3 : 0', 0, 0, ''
from player p 
	inner join typeperplayer tp ON p.Play_ID = tp.typl_play_id
