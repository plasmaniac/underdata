package no.underthehood.nbim.report

import no.underthehood.nbim.model.EntityYear
import no.underthehood.nbim.model.NBIMPosition
import org.springframework.stereotype.Service
import org.sql2o.Sql2o
import javax.sql.DataSource

interface NBIMEquityDao {
    fun getAll(): List<NBIMPosition>
    fun getByCountryAndYear(country: String, year: Int): List<NBIMPosition>
    fun getByIndustryAndYear(industry: String, year: Int): List<NBIMPosition>
    fun getDistinctCountries(): List<String>
    fun getDistinctYears(): List<Int>
    fun getDistinctCompanies(): List<String>
    fun getCountryDevelopment(country: String): List<EntityYear>
    fun getCompanyDevelopment(company: String): List<EntityYear>

}

@Service
class NBIMEquityDaoImpl(
    dataSource: DataSource
) :NBIMEquityDao {

    val sql2o = Sql2o(dataSource)

    override fun getAll(): List<NBIMPosition> {
        return sql2o.open().use { connection ->
            connection.createQuery(
                """
               select "Region", 
               "Country",
               "Name",
               "Industry",
               "Market Value(NOK)" as marketValueNOK,
               "Market Value(USD)" as marketValueUSD,
               "Voting", 
               "Ownership",
               "Incorporation Country" as incorporationCountry, 
               "ReportingYear" from eq
                """.trimIndent()
            )
                .executeAndFetch(NBIMPosition::class.java)
        }
    }

    override fun getByCountryAndYear(
        country: String,
        year: Int
    ): List<NBIMPosition> {
        return sql2o.open().use { connection ->
            connection.createQuery(
                """
               select "Region", 
               "Country",
               "Name",
               "Industry",
               "Market Value(NOK)" as marketValueNOK,
               "Market Value(USD)" as marketValueUSD,
               "Voting", 
               "Ownership",
               "Incorporation Country" as incorporationCountry, 
               "ReportingYear" from eq 
               where "Country"=:country
               and
               "ReportingYear"=:year
                """.trimIndent()
            )
                .addParameter("country", country)
                .addParameter("year", year)
                .executeAndFetch(NBIMPosition::class.java)
        }
    }

    override fun getByIndustryAndYear(industry: String, year: Int): List<NBIMPosition> {
        return sql2o.open().use { connection ->
            connection.createQuery(
                """
               select "Region", 
               "Country",
               "Name",
               "Industry",
               "Market Value(NOK)" as marketValueNOK,
               "Market Value(USD)" as marketValueUSD,
               "Voting", 
               "Ownership",
               "Incorporation Country" as incorporationCountry, 
               "ReportingYear" from eq 
               where "Industry"=:industry
               and
               "ReportingYear"=:year
                """.trimIndent()
            )
                .addParameter("industry", industry)
                .addParameter("year", year)
                .executeAndFetch(NBIMPosition::class.java)
        }
    }

    override fun getDistinctCountries(): List<String> {
        return sql2o.open().use { connection ->
            connection.createQuery(
                """
               select distinct "Country"  from eq order by "Country"
                """.trimIndent()
            ).executeAndFetchTable().rows().map { it.getString("Country") }
        }
    }

    override fun getDistinctYears(): List<Int> {
        return sql2o.open().use { connection ->
            connection.createQuery(
                """
               select distinct "ReportingYear" as Year from eq order by "ReportingYear" desc
                """.trimIndent()
            ).executeAndFetchTable().rows().map { it.getInteger("Year") }
        }
    }

    override fun getDistinctCompanies(): List<String> {
        return sql2o.open().use { connection ->
            connection.createQuery(
                """
               select distinct "Name"  from eq order by "Name" desc
                """.trimIndent()
            ).executeAndFetchTable().rows().map { it.getString("Name") }
        }
    }

    override fun getCountryDevelopment(country: String): List<EntityYear> {
        return sql2o.open().use { connection ->
            connection.createQuery(
                """
                select "ReportingYear" as year , SUM("Market Value(NOK)") as sum, "Country" as entity 
                from eq  
                group by "Country","ReportingYear" 
                having "Country" = :country order by "ReportingYear" desc;
                """.trimIndent()
            )
                .addParameter("country", country)
                .executeAndFetch(EntityYear::class.java)
        }
    }

    override fun getCompanyDevelopment(company: String): List<EntityYear> {
        return sql2o.open().use { connection ->
            connection.createQuery(
                """
                select "ReportingYear" as year , SUM("Market Value(NOK)") as sum, "Name" as entity  
                from eq  
                group by "Name","ReportingYear" 
                having "Name" = :company order by "ReportingYear" desc;
                """.trimIndent()
            )
                .addParameter("company", company)
                .executeAndFetch(EntityYear::class.java)
        }
    }
}
