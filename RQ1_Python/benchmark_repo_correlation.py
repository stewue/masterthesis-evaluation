import pandas as pd
import numpy as np
from correlation_helper import pointbiserialr, spearman, dfOutput

def point_correlation_repo_meta(col, data):
    cleaned = data[np.logical_not(np.isnan(data[col]))]
    return pd.DataFrame({
            "name":col,
            "stars":pointbiserialr(cleaned, col, 'stars'),
            "projectForks":pointbiserialr(cleaned, col, 'projectForks'),
            "watchers":pointbiserialr(cleaned, col, 'watchers'),
            "numberOfCommits":pointbiserialr(cleaned, col, 'numberOfCommits'),
            "numberOfContributors":pointbiserialr(cleaned, col, 'numberOfContributors')
    })

def spearman_correlation_repo_meta(col, data):
    return pd.DataFrame({
            "name":col,
            "stars":spearman(data, col, 'stars'),
            "projectForks":spearman(data, col, 'projectForks'),
            "watchers":spearman(data, col, 'watchers'),
            "numberOfCommits":spearman(data, col, 'numberOfCommits'),
            "numberOfContributors":spearman(data, col, 'numberOfContributors')
    })

pd.set_option('display.max_rows', 500)
pd.set_option('display.max_columns', 500)
pd.set_option('display.width', 1000)

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\benchmarkrepocorrelation.csv')

df = pd.DataFrame({})
df = df.append(spearman_correlation_repo_meta('warmupIterations', data), ignore_index = True)
df = df.append(spearman_correlation_repo_meta('warmupTime', data), ignore_index = True)
df = df.append(spearman_correlation_repo_meta('measurementIterations', data), ignore_index = True)
df = df.append(spearman_correlation_repo_meta('measurementTime', data), ignore_index = True)
df = df.append(spearman_correlation_repo_meta('forks', data), ignore_index = True)
df = df.append(spearman_correlation_repo_meta('warmupForks', data), ignore_index = True)

print(dfOutput(df))

print("------------")

df = pd.DataFrame({})
df = df.append(point_correlation_repo_meta('modeIsThroughput', data), ignore_index = True)
df = df.append(point_correlation_repo_meta('modeIsAverageTime', data), ignore_index = True)
df = df.append(point_correlation_repo_meta('modeIsSampleTime', data), ignore_index = True)
df = df.append(point_correlation_repo_meta('modeIsSingleShotTime', data), ignore_index = True)

print(dfOutput(df))

print("------------")

df = pd.DataFrame({})
df = df.append(point_correlation_repo_meta('nothingSet', data), ignore_index = True)
df = df.append(point_correlation_repo_meta('warmupIterationsDefault', data), ignore_index = True)
df = df.append(point_correlation_repo_meta('warmupTimeDefault', data), ignore_index = True)
df = df.append(point_correlation_repo_meta('measurementIterationsDefault', data), ignore_index = True)
df = df.append(point_correlation_repo_meta('measurementTimeDefault', data), ignore_index = True)
df = df.append(point_correlation_repo_meta('forksDefault', data), ignore_index = True)
df = df.append(point_correlation_repo_meta('warmupForksDefault', data), ignore_index = True)
df = df.append(point_correlation_repo_meta('modeDefault', data), ignore_index = True)

print(dfOutput(df))