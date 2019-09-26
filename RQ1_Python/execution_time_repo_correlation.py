import pandas as pd

pd.set_option('display.max_rows', 500)
pd.set_option('display.max_columns', 500)
pd.set_option('display.width', 1000)

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\executiontimerepocorrelation.csv')

data['measurementWarmupRatio'] = pd.to_numeric(data['measurementWarmupRatio'], 'coerce')

selected = data[['executionTimePercentage', 'warmupTimePercentage', 'measurementTimePercentage', 'measurementWarmupRatio',
                 'stars', 'forks', 'watchers', 'numberOfCommits', 'numberOfContributors', 'numberOfBenchmarks']]

corr = selected.corr('spearman').round(2)
print(corr.to_csv(index=False))