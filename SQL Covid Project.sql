--Covid-19 Data Exploration
--Demonstrated: Joins, CTE's, Temp Tables, Windows Functions, Aggregate Functions, Creating Views, Converting Data Types

Select *
From CovidProject..CovidDeaths
Where continent is not null
order by 3,4

--Selecting data to start with
Select location, date, total_cases, new_cases, total_deaths, population
From CovidProject..CovidDeaths
Where continent is not null
order by 1,2

--Total cases vs total deaths
--Shows likelihood of dying if COVID-19 is contracted in Canada
Select location, date, total_cases, total_deaths, (total_deaths/total_cases)*100 as DeathPercentage
From CovidProject..CovidDeaths
Where location like '%canada%' and continent is not null
order by 1,2

--Total cases vs population
--Shows percentage of population infected with COVID-19
Select location, date, population, total_cases, (total_cases/population)*100 as PercentPopulationInfected
From CovidProject..CovidDeaths
order by 1,2

--Countries with highest infection rate when compared to population
Select location, population, Max(total_cases) as HighestInfectionCount, Max((total_cases/population))*100 as PercentPopulationInfected
From CovidProject..CovidDeaths
Group by location, population
order by PercentPopulationInfected desc

--Countries with highest death count per population
Select location, Max(cast(total_deaths as int)) as TotalDeathCount
From CovidProject..CovidDeaths
Where continent is not null
Group by location
order by TotalDeathCount desc

--ANALYSIS BY CONTINENT

--Showing continents with the highest death count per population
Select continent, Max(cast(total_deaths as int)) as TotalDeathCount
From CovidProject..CovidDeaths
Where continent is not null
Group by continent
order by TotalDeathCount desc

--GLOBAL NUMBERS
Select Sum(new_cases) as total_cases, Sum(cast(new_deaths as int)) as total_deaths, Sum(cast(new_deaths as int))/Sum(new_cases)*100 as DeathPercentage
From CovidProject..CovidDeaths
Where continent is not null
order by 1,2 

--Total population vs vaccinations
Select dea.continent, dea.location, dea.date, dea.population, vac.new_vaccinations, 
Sum(convert(bigint,vac.new_vaccinations)) OVER (Partition by dea.location Order by dea.location, dea.date) as RollingPeopleVaccinated
From CovidProject..CovidDeaths dea
Join CovidProject..CovidVaccinations vac
	On dea.location = vac.location and dea.date = vac.date
Where dea.continent is not null
order by 2,3

--Using CTE to perform calculation on Partition By in previous query
With PopulationvsVaccinations (continent, location, date, population, new_vaccinations, RollingPeopleVaccinated) 
as
(
Select dea.continent, dea.location, dea.date, dea.population, vac.new_vaccinations, Sum(convert(bigint, vac.new_vaccinations)) OVER (Partition by dea.location order by dea.location, dea.date) as RollingPeopleVaccinated
From CovidProject..CovidDeaths dea
Join CovidProject..CovidVaccinations vac
	On dea.location = vac.location and dea.date = vac.date
Where dea.continent is not null
)

Select *, (RollingPeopleVaccinated/population)*100 
From PopulationvsVaccinations

--Using Temp Table to perform calculation on Partition By in previous query
DROP Table if exists #PercentPopulationVaccinated
Create Table #PercentPopulationVaccinated
(
Continent nvarchar(255),
Location nvarchar(255),
Date datetime,
Population numeric,
New_vaccinations numeric,
RollingPeopleVaccinated numeric
)

Insert into #PercentPopulationVaccinated
Select dea.continent, dea.location, dea.date, dea.population, vac.new_vaccinations, Sum(convert(bigint, vac.new_vaccinations)) OVER (Partition by dea.location Order by dea.location, dea.date) as RollingPeopleVaccinated
From CovidProject..CovidDeaths dea
Join CovidProject..CovidVaccinations vac
	On dea.location = vac.location and dea.date = vac.date

Select *, (RollingPeopleVaccinated/population)*100
From #PercentPopulationVaccinated

--Creating View to store data for visualizations
Create View PercentagePopulationVaccinated as
Select dea.continent, dea.location, dea.date, dea.population, vac.new_vaccinations, SUM(CONVERT(bigint,vac.new_vaccinations)) OVER (Partition by dea.location Order by dea.location, dea.date) as RollingPeopleVaccinated
From CovidProject..CovidDeaths dea
Join CovidProject..CovidVaccinations vac
	On dea.location = vac.location and dea.date = vac.date
Where dea.continent is not null