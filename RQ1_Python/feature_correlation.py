import pandas as pd

pd.set_option('display.max_rows', 500)
pd.set_option('display.max_columns', 500)
pd.set_option('display.width', 1000)

data = pd.read_csv('D:\\mp\\out.csv')
data['parametrizationUsed'] = data.apply(lambda row: row.parametrizationUsedPercentage > 0, axis=1)
data['returnTypeOrBlackholeUsed'] = data.apply(lambda row: row.returnTypeOrBlackholeUsedPercentage > 0, axis=1)
data['hasStateObjectsWithoutJmhParams'] = data.apply(lambda row: row.hasStateObjectsWithoutJmhParamsPercentage > 0,
                                                     axis=1)

selected = data[
    ['avgNumberOfBenchmarksPerClass', 'parametrizationUsed', 'parametrizationUsedPercentage', 'groupsUsedPercentage',
     'blackholeUsedPercentage', 'returnTypeUsedPercentage', 'returnTypeOrBlackholeUsed',
     'returnTypeOrBlackholeUsedPercentage', 'controlUsedPercentage', 'hasStateObjectsWithJmhParamsPercentage',
     'hasStateObjectsWithoutJmhParams', 'hasStateObjectsWithoutJmhParamsPercentage', 'stars', 'forks', 'watchers',
     'numberOfCommits', 'numberOfContributors', 'numberOfBenchmarks']]

corr = selected.corr()
print(corr.to_csv(index=False))
