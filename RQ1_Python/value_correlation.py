import pandas as pd
from correlation_helper import spearman, dfOutput

def spearman_correlation_config_options(name, col, data):
    return pd.DataFrame({
        "name": name,
        "warmupIterations": spearman(data, col, 'warmupIterations'),
        "warmupTime": spearman(data, col, 'warmupTime'),
        "measurementIterations": spearman(data, col, 'measurementIterations'),
        "measurementTime": spearman(data, col, 'measurementTime'),
        "forks": spearman(data, col, 'forks'),
        "warmupForks": spearman(data, col, 'warmupForks'),
    })

pd.set_option('display.max_rows', 500)
pd.set_option('display.max_columns', 500)
pd.set_option('display.width', 1000)

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\valuecorrelation.csv')

df = pd.DataFrame({})
df = df.append(spearman_correlation_config_options('warmup iterations', 'warmupIterations', data), ignore_index=True)
df = df.append(spearman_correlation_config_options('warmup time', 'warmupTime', data), ignore_index=True)
df = df.append(spearman_correlation_config_options('measurement iterations', 'measurementIterations', data), ignore_index=True)
df = df.append(spearman_correlation_config_options('measurement time', 'measurementTime', data), ignore_index=True)
df = df.append(spearman_correlation_config_options('forks', 'forks', data), ignore_index=True)
df = df.append(spearman_correlation_config_options('warmup forks', 'warmupForks', data), ignore_index=True)

print(dfOutput(df))
