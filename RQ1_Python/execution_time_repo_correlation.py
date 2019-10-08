import pandas as pd
from correlation_helper import spearman, dfOutput

def spearman_correlation_repo_meta(name, col, data):
    return pd.DataFrame({
        "name": name,
        "stars": spearman(data, col, 'stars'),
        "forks": spearman(data, col, 'forks'),
        "watchers": spearman(data, col, 'watchers'),
        "numberOfCommits": spearman(data, col, 'numberOfCommits'),
        "numberOfContributors": spearman(data, col, 'numberOfContributors'),
        "parameterizationCombinations": spearman(data, col, 'parameterizationCombinations')
    })

pd.set_option('display.max_rows', 500)
pd.set_option('display.max_columns', 500)
pd.set_option('display.width', 1000)

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\executiontimerepocorrelation.csv')

data['measurementWarmupRatio'] = pd.to_numeric(data['measurementWarmupRatio'], 'coerce')

df = pd.DataFrame({})
df = df.append(spearman_correlation_repo_meta('used execution time', 'executionTimePercentage', data), ignore_index=True)
df = df.append(spearman_correlation_repo_meta('measurementWarmupRatio', 'measurementWarmupRatio', data), ignore_index=True)

print(dfOutput(df))