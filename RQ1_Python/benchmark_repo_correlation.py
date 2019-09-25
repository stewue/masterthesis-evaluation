import pandas as pd
import numpy as np
from scipy import stats

def create_df(col):
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
df = df.append(create_df('modeIsThroughput'), ignore_index = True)
df = df.append(create_df('modeIsAverageTime'), ignore_index = True)
df = df.append(create_df('modeIsSampleTime'), ignore_index = True)
df = df.append(create_df('modeIsSingleShotTime'), ignore_index = True)

print(df.to_csv(index=False))

print("------------")

df = pd.DataFrame({})
df = df.append(create_df('nothingSet'), ignore_index = True)
df = df.append(create_df('warmupIterationsDefault'), ignore_index = True)
df = df.append(create_df('warmupTimeDefault'), ignore_index = True)
df = df.append(create_df('measurementIterationsDefault'), ignore_index = True)
df = df.append(create_df('measurementTimeDefault'), ignore_index = True)
df = df.append(create_df('forksDefault'), ignore_index = True)
df = df.append(create_df('warmupForksDefault'), ignore_index = True)
df = df.append(create_df('modeDefault'), ignore_index = True)

print(df.to_csv(index=False))