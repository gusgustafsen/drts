package gov.ed.fsa.drts.sql;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

public interface CustomTaskMapper {

	@Select({"SELECT * FROM(SELECT T2.*, rownum AS ROW_NUM FROM(SELECT T.* FROM(SELECT * FROM VIEW_PROCESS_VARIABLES) T WHERE CANDIDATE_GROUP IN ${param1} OR ASSIGNEE = '${param2}' ORDER BY ${param3} ${param4}) T2) T3 WHERE ROW_NUM > ${param5}  AND ROW_NUM <= ${param6}"})
    List<Map<String, Object>> selectTasks(String candidate_groups, String assigned_to, String order_by, String sort, int first_row, int last_row);
	
	// TODO why are parameters required?
	@Select({"SELECT COUNT(*) FROM VIEW_PROCESS_VARIABLES WHERE CANDIDATE_GROUP IN ${param1} OR ASSIGNEE = '${param2}'"})
    Integer selectTaskCount(String candidate_groups, String assigned_to, String order_by, String sort, int first_row, int last_row);
}
