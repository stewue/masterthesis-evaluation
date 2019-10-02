import pandas as pd
from correlation_helper import spearman, dfOutput

def spearman_correlation_config_options(col, data):
    return pd.DataFrame({
        "name": col,
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

data = pd.read_csv('D:\\mp\\corr.csv')

df = pd.DataFrame({})
df = df.append(spearman_correlation_config_options('warmupIterations', data), ignore_index = True)
df = df.append(spearman_correlation_config_options('warmupTime', data), ignore_index = True)
df = df.append(spearman_correlation_config_options('measurementIterations', data), ignore_index = True)
df = df.append(spearman_correlation_config_options('measurementTime', data), ignore_index = True)
df = df.append(spearman_correlation_config_options('forks', data), ignore_index = True)
df = df.append(spearman_correlation_config_options('warmupForks', data), ignore_index = True)

print(dfOutput(df))
