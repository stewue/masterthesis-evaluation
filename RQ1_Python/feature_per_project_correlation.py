import pandas as pd
from correlation_helper import spearman, dfOutput

def spearman_correlation_repo_meta(name, col, data):
    return pd.DataFrame({
        "name": name,
        "stars": spearman(data, col, 'stars'),
        "projectForks": spearman(data, col, 'forks'),
        "watchers": spearman(data, col, 'watchers'),
        "numberOfCommits": spearman(data, col, 'numberOfCommits'),
        "numberOfContributors": spearman(data, col, 'numberOfContributors')
    })

pd.set_option('display.max_rows', 500)
pd.set_option('display.max_columns', 500)
pd.set_option('display.width', 1000)

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\featuresperproject.csv')

df = pd.DataFrame({})
df = df.append(spearman_correlation_repo_meta('average number of benchmarks per class', 'avgNumberOfBenchmarksPerClass', data), ignore_index = True)
df = df.append(spearman_correlation_repo_meta('parameterization used', 'parameterizationUsedPercentage', data), ignore_index = True)
df = df.append(spearman_correlation_repo_meta('groups used', 'groupsUsedPercentage', data), ignore_index = True)
df = df.append(spearman_correlation_repo_meta('blackhole used', 'blackholeUsedPercentage', data), ignore_index = True)
df = df.append(spearman_correlation_repo_meta('state object without \\acs{JMH} Parameters used', 'hasStateObjectsWithoutJmhParamsPercentage', data), ignore_index = True)
df = df.append(spearman_correlation_repo_meta('return type used', 'returnTypeUsedPercentage', data), ignore_index = True)
df = df.append(spearman_correlation_repo_meta('return type or blackhole used', 'returnTypeOrBlackholeUsedPercentage', data), ignore_index = True)
df = df.append(spearman_correlation_repo_meta('number of benchmarks', 'numberOfBenchmarks', data), ignore_index = True)

print(dfOutput(df))