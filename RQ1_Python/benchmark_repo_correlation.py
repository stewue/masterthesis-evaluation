import pandas as pd
import numpy as np
from scipy import stats

def create_df(col, data):
    cleaned = data[np.logical_not(np.isnan(data[col]))]
    return pd.DataFrame({
            "name":col,
            "stars":[stats.pointbiserialr(cleaned[col], cleaned['stars']).correlation.round(2)],
            "projectForks":[stats.pointbiserialr(cleaned[col], cleaned['projectForks']).correlation.round(2)],
            "watchers":[stats.pointbiserialr(cleaned[col], cleaned['watchers']).correlation.round(2)],
            "numberOfCommits":[stats.pointbiserialr(cleaned[col], cleaned['numberOfCommits']).correlation.round(2)],
            "numberOfContributors":[stats.pointbiserialr(cleaned[col], cleaned['numberOfContributors']).correlation.round(2)],
            "numberOfBenchmarks":[stats.pointbiserialr(cleaned[col], cleaned['numberOfBenchmarks']).correlation.round(2)]
    })

pd.set_option('display.max_rows', 500)
pd.set_option('display.max_columns', 500)
pd.set_option('display.width', 1000)

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\benchmarkrepocorrelation.csv')

selected = data[['warmupIterations', 'warmupTime', 'measurementIterations', 'measurementTime', 'forks', 'warmupForks',
                 'stars', 'projectForks', 'watchers', 'numberOfCommits', 'numberOfContributors', 'numberOfBenchmarks']]

corr = selected.corr('spearman').round(2)
print(corr.to_csv(index=False))

print("------------")

df = pd.DataFrame({})
df = df.append(create_df('modeIsThroughput', data), ignore_index = True)
df = df.append(create_df('modeIsAverageTime', data), ignore_index = True)
df = df.append(create_df('modeIsSampleTime', data), ignore_index = True)
df = df.append(create_df('modeIsSingleShotTime', data), ignore_index = True)

print(df.to_csv(index=False))

print("------------")

df = pd.DataFrame({})
df = df.append(create_df('nothingSet', data), ignore_index = True)
df = df.append(create_df('warmupIterationsDefault', data), ignore_index = True)
df = df.append(create_df('warmupTimeDefault', data), ignore_index = True)
df = df.append(create_df('measurementIterationsDefault', data), ignore_index = True)
df = df.append(create_df('measurementTimeDefault', data), ignore_index = True)
df = df.append(create_df('forksDefault', data), ignore_index = True)
df = df.append(create_df('warmupForksDefault', data), ignore_index = True)
df = df.append(create_df('modeDefault', data), ignore_index = True)

print(df.to_csv(index=False))