import pandas as pd

pd.set_option('display.max_rows', 500)
pd.set_option('display.max_columns', 500)
pd.set_option('display.width', 1000)

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\featuresperproject - Kopie.csv')
data['parametrizationUsed'] = data.apply(lambda row: row.parametrizationUsedPercentage > 0, axis=1)
data['returnTypeOrBlackholeUsed'] = data.apply(lambda row: row.returnTypeOrBlackholeUsedPercentage > 0, axis=1)
data['hasStateObjectsWithoutJmhParams'] = data.apply(lambda row: row.hasStateObjectsWithoutJmhParamsPercentage > 0,
                                                     axis=1)

selected = data[['avgNumberOfBenchmarksPerClass', 'parametrizationUsedPercentage', 'groupsUsedPercentage', 'blackholeUsedPercentage',
                 'controlUsedPercentage', 'hasStateObjectsWithoutJmhParamsPercentage', 'returnTypeUsedPercentage', 'returnTypeOrBlackholeUsedPercentage',
                 'hasStateObjectsWithJmhParamsPercentage',  'benchmarkIsInnerClassPercentage', 'stars', 'forks', 'watchers', 'numberOfCommits', 'numberOfContributors', 'numberOfBenchmarks']]

corr = selected.corr('spearman').round(2)
print(corr.to_csv(index=False))
