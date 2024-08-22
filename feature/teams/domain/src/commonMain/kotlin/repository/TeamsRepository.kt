package teams

import kotlinx.coroutines.flow.Flow
import teams.Team
import utils.Resource

interface TeamsRepository {
    fun getTeams(): Flow<Resource<List<Team>>>
    suspend fun getTeamByTeamName(name: String): Team?
    suspend fun clear()
}