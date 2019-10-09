import pandas as pd
import numpy as np
from correlation_helper import pointbiserialr, spearman, dfOutput

def point_correlation_repo_meta(name, col, data):
    cleaned = data[np.logical_not(np.isnan(data[col]))]
    return pd.DataFrame({
        "name": name,
        "stars": pointbiserialr(cleaned, col, 'stars'),
        "projectForks": pointbiserialr(cleaned, col, 'projectForks'),
        "watchers": pointbiserialr(cleaned, col, 'watchers'),
        "numberOfCommits": pointbiserialr(cleaned, col, 'numberOfCommits'),
        "numberOfContributors": pointbiserialr(cleaned, col, 'numberOfContributors')
    })


def spearman_correlation_repo_meta(name, col, data):
    return pd.DataFrame({
        "name": name,
        "stars": spearman(data, col, 'stars'),
        "projectForks": spearman(data, col, 'projectForks'),
        "watchers": spearman(data, col, 'watchers'),
        "numberOfCommits": spearman(data, col, 'numberOfCommits'),
        "numberOfContributors": spearman(data, col, 'numberOfContributors')
    })


pd.set_option('display.max_rows', 500)
pd.set_option('display.max_columns', 500)
pd.set_option('display.width', 1000)

data = pd.read_csv(
    'C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\benchmarkrepocorrelation.csv')

df = pd.DataFrame({})
df = df.append(spearman_correlation_repo_meta('warmup iterations', 'warmupIterations', data), ignore_index=True)
df = df.append(spearman_correlation_repo_meta('warmup time', 'warmupTime', data), ignore_index=True)
df = df.append(spearman_correlation_repo_meta('measurement iterations', 'measurementIterations', data), ignore_index=True)
df = df.append(spearman_correlation_repo_meta('measurement time', 'measurementTime', data), ignore_index=True)
df = df.append(spearman_correlation_repo_meta('forks', 'forks', data), ignore_index=True)
df = df.append(spearman_correlation_repo_meta('warmup forks', 'warmupForks', data), ignore_index=True)

print(dfOutput(df))

print("------------")

df = pd.DataFrame({})
df = df.append(point_correlation_repo_meta('mode is throughput', 'modeIsThroughput', data), ignore_index=True)
df = df.append(point_correlation_repo_meta('mode is average time', 'modeIsAverageTime', data), ignore_index=True)
df = df.append(point_correlation_repo_meta('mode is sample time', 'modeIsSampleTime', data), ignore_index=True)
df = df.append(point_correlation_repo_meta('mode is singleshot', 'modeIsSingleShotTime', data), ignore_index=True)

print(dfOutput(df))

print("------------")

df = pd.DataFrame({})
data['somethingSet'] = -1 * data['nothingSet']
df = df.append(point_correlation_repo_meta('at least a configuration option is set', 'somethingSet', data), ignore_index=True)
data['invertedWarmupIterationsDefault'] = -1 * data['warmupIterationsDefault']
df = df.append(point_correlation_repo_meta('non-default warmup iteration value used', 'invertedWarmupIterationsDefault', data), ignore_index=True)
data['invertedWarmupTimeDefault'] = -1 * data['warmupTimeDefault']
df = df.append(point_correlation_repo_meta('non-default warmup time value used', 'invertedWarmupTimeDefault', data), ignore_index=True)
data['invertedMeasurementIterationsDefault'] = -1 * data['measurementIterationsDefault']
df = df.append(point_correlation_repo_meta('non-default measurement iteration value used', 'invertedMeasurementIterationsDefault', data), ignore_index=True)
data['invertedMeasurementTimeDefault'] = -1 * data['measurementTimeDefault']
df = df.append(point_correlation_repo_meta('non-default measurement time value used', 'invertedMeasurementTimeDefault', data), ignore_index=True)
data['invertedForksDefault'] = -1 * data['forksDefault']
df = df.append(point_correlation_repo_meta('non-default fork value used', 'invertedForksDefault', data), ignore_index=True)
data['invertedWarmupForksDefault'] = -1 * data['warmupForksDefault']
df = df.append(point_correlation_repo_meta('non-default warmup fork value used', 'invertedWarmupForksDefault', data), ignore_index=True)
data['invertedModeDefault'] = -1 * data['modeDefault']
df = df.append(point_correlation_repo_meta('non-default mode used', 'invertedModeDefault', data), ignore_index=True)

print(dfOutput(df))
