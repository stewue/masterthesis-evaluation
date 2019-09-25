import pandas as pd

pd.set_option('display.max_rows', 500)
pd.set_option('display.max_columns', 500)
pd.set_option('display.width', 1000)

data = pd.read_csv('D:\\mp\\corr.csv')

corr = data.corr('spearman').round(2)
print(corr.to_csv(index=False))
